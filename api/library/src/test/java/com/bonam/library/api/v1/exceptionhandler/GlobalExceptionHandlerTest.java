package com.bonam.library.api.v1.exceptionhandler;

import com.bonam.library.api.v1.exception.ActiveLoanExistsException;
import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.api.v1.model.response.LibraryErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    void handleAllExceptions_ShouldReturnInternalServerError() {
        Exception exception = new RuntimeException("Test exception");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<LibraryErrorResponse> responseEntity = globalExceptionHandler.handleAllExceptions(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatus());
        assertEquals("Test exception", responseEntity.getBody().getMessage());
        assertEquals("/api/test", responseEntity.getBody().getPath());
    }

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFound() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found", "1");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<LibraryErrorResponse> responseEntity = globalExceptionHandler.handleResourceNotFoundException(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatus());
        assertEquals("/api/test", responseEntity.getBody().getPath());
    }

    @Test
    void handleMethodArgumentNotValid_ShouldReturnBadRequest() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        FieldError fieldError = new FieldError("objectName", "field", "Field error message");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleMethodArgumentNotValid(
                exception,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        LibraryErrorResponse errorResponse = (LibraryErrorResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
        assertEquals("field: Field error message", errorResponse.getMessage());
        assertEquals("/api/test", errorResponse.getPath());
        assertEquals("Validation Failed", errorResponse.getError());
    }

    @Test
    void handleActiveLoanExistsException_ShouldReturnConflict() {
        ActiveLoanExistsException exception = new ActiveLoanExistsException("Book", "1");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<LibraryErrorResponse> responseEntity = globalExceptionHandler.handleActiveLoanExistsException(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getBody().getStatus());
        assertEquals("Book already has an active loan with identifier: 1", responseEntity.getBody().getMessage());
        assertEquals("/api/test", responseEntity.getBody().getPath());
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), responseEntity.getBody().getError());
    }

    @Test
    void handleConstraintViolationException_ShouldReturnBadRequest() {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);

        when(path.toString()).thenReturn("nome");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("não pode ser vazio");
        violations.add(violation);

        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        when(exception.getConstraintViolations()).thenReturn(violations);

        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<LibraryErrorResponse> responseEntity =
                globalExceptionHandler.handleConstraintViolationException(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("nome: não pode ser vazio", responseEntity.getBody().getMessage());
    }

    @Test
    void handleTransactionSystemException_WithConstraintViolation_ShouldDelegateToConstraintHandler() {
        TransactionSystemException transactionException = mock(TransactionSystemException.class);
        ConstraintViolationException constraintException = mock(ConstraintViolationException.class);

        when(transactionException.getRootCause()).thenReturn(constraintException);

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);

        when(path.toString()).thenReturn("campo");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("erro de validação");
        violations.add(violation);

        when(constraintException.getConstraintViolations()).thenReturn(violations);

        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<LibraryErrorResponse> responseEntity =
                globalExceptionHandler.handleTransactionSystemException(transactionException, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("campo: erro de validação", responseEntity.getBody().getMessage());
    }

    @Test
    void handleTransactionSystemException_WithOtherCause_ShouldReturnBadRequest() {
        TransactionSystemException transactionException = mock(TransactionSystemException.class);
        RuntimeException cause = new RuntimeException("Erro na transação");

        when(transactionException.getRootCause()).thenReturn(cause);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        ResponseEntity<LibraryErrorResponse> responseEntity =
                globalExceptionHandler.handleTransactionSystemException(transactionException, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Erro na transação", responseEntity.getBody().getMessage());
    }
}

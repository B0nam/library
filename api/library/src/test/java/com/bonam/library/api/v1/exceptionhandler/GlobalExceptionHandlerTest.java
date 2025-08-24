package com.bonam.library.api.v1.exceptionhandler;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.api.v1.model.response.LibraryErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

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
}

package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.CreateBookService;
import com.bonam.library.domain.googlebooks.service.GetGoogleBooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateGoogleBookControllerTest {

    @Mock
    private GetGoogleBooksService getGoogleBooksService;

    @Mock
    private CreateBookService createBookService;

    @InjectMocks
    private CreateGoogleBookController controller;

    private Book mockBook;
    private final String GOOGLE_BOOK_ID = "abc123";

    @BeforeEach
    void setUp() {
        mockBook = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("9781234567890")
                .publishDate(LocalDate.now())
                .category("Fiction")
                .build();
    }

    @Test
    void shouldCreateBookSuccessfully() {
        when(getGoogleBooksService.getBookById(GOOGLE_BOOK_ID)).thenReturn(mockBook);
        when(createBookService.createBook(any(Book.class))).thenReturn(mockBook);

        ResponseEntity<Book> response = controller.createBookOnGoogleBookId(GOOGLE_BOOK_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBook, response.getBody());

        verify(getGoogleBooksService).getBookById(GOOGLE_BOOK_ID);
        verify(createBookService).createBook(mockBook);
    }

    @Test
    void shouldPropagateExceptionWhenGoogleBookNotFound() {
        when(getGoogleBooksService.getBookById(GOOGLE_BOOK_ID))
                .thenThrow(new ResourceNotFoundException("Google Book", GOOGLE_BOOK_ID));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> controller.createBookOnGoogleBookId(GOOGLE_BOOK_ID)
        );

        assertEquals("Google Book not found with identifier: abc123", exception.getMessage());
        verify(getGoogleBooksService).getBookById(GOOGLE_BOOK_ID);
        verify(createBookService, never()).createBook(any(Book.class));
    }

    @Test
    void shouldPropagateExceptionWhenBookCreationFails() {
        when(getGoogleBooksService.getBookById(GOOGLE_BOOK_ID)).thenReturn(mockBook);
        when(createBookService.createBook(any(Book.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> controller.createBookOnGoogleBookId(GOOGLE_BOOK_ID)
        );

        assertEquals("Database error", exception.getMessage());
        verify(getGoogleBooksService).getBookById(GOOGLE_BOOK_ID);
        verify(createBookService).createBook(mockBook);
    }
}

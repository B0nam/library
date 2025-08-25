package com.bonam.library.api.v1.controller;

import com.bonam.library.domain.googlebooks.model.GoogleBookItemDTO;
import com.bonam.library.domain.googlebooks.service.GetGoogleBooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGoogleBookControllerTest {

    @Mock
    private GetGoogleBooksService googleBooksService;

    @InjectMocks
    private GetGoogleBookController controller;

    private GoogleBookItemDTO mockGoogleBook;
    private final String BOOK_TITLE = "Test Title";

    @BeforeEach
    void setUp() {
        mockGoogleBook = new GoogleBookItemDTO();
        mockGoogleBook.setId("abc123");
    }

    @Test
    void shouldReturnListOfBooksWhenSearchingByTitle() {
        List<GoogleBookItemDTO> bookList = Collections.singletonList(mockGoogleBook);
        when(googleBooksService.searchBooksByTitle(BOOK_TITLE)).thenReturn(bookList);

        ResponseEntity<List<GoogleBookItemDTO>> response = controller.searchBooksByTitle(BOOK_TITLE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookList, response.getBody());
        assertEquals(1, response.getBody().size());
        verify(googleBooksService).searchBooksByTitle(BOOK_TITLE);
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksFound() {
        List<GoogleBookItemDTO> emptyList = new ArrayList<>();
        when(googleBooksService.searchBooksByTitle(BOOK_TITLE)).thenReturn(emptyList);

        ResponseEntity<List<GoogleBookItemDTO>> response = controller.searchBooksByTitle(BOOK_TITLE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(googleBooksService).searchBooksByTitle(BOOK_TITLE);
    }

    @Test
    void shouldPropagateExceptionWhenServiceThrowsException() {
        when(googleBooksService.searchBooksByTitle(BOOK_TITLE)).thenThrow(new RuntimeException("API Error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> controller.searchBooksByTitle(BOOK_TITLE)
        );

        assertEquals("API Error", exception.getMessage());
        verify(googleBooksService).searchBooksByTitle(BOOK_TITLE);
    }
}

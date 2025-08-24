package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.domain.books.service.DeleteBookService;

@ExtendWith(MockitoExtension.class)
class DeleteBookControllerTest {

    @Mock
    private DeleteBookService deleteBookService;

    @InjectMocks
    private DeleteBookController controller;

    @Test
    void shouldDeleteBookSuccessfully() {
        Long bookId = 1L;
        doNothing().when(deleteBookService).deleteBook(anyLong());

        var response = controller.deleteBook(bookId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteBookService).deleteBook(bookId);
    }
}

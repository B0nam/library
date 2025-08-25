package com.bonam.library.domain.books.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private DeleteBookService deleteBookService;

    @Test
    void shouldDeleteBookSuccessfully() {
        Long bookId = 1L;
        LocalDate publishDate = LocalDate.now();

        Book existingBook = Book.builder()
                .id(bookId)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingBook));
        doNothing().when(bookRepository).delete(any(Book.class));

        deleteBookService.deleteBook(bookId);

        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(existingBook);
    }

    @Test
    void shouldThrowNotFoundWhenBookDoesNotExist() {
        Long bookId = 1L;
        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deleteBookService.deleteBook(bookId));
    }
}

package com.bonam.library.domain.books.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class UpdateBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private UpdateBookService updateBookService;

    @Test
    void shouldUpdateBookSuccessfully() {
        Long bookId = 1L;
        LocalDate originalDate = LocalDate.now();
        LocalDate newDate = LocalDate.now().plusDays(1);

        Book existingBook = Book.builder()
                .id(bookId)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(originalDate)
                .category("Programming")
                .build();

        Book updateRequest = Book.builder()
                .title("Clean Code: Second Edition")
                .author("Robert C. Martin")
                .isbn("9780132350885")
                .publishDate(newDate)
                .category("Software Engineering")
                .build();

        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Clean Code: Second Edition")
                .author("Robert C. Martin")
                .isbn("9780132350885")
                .publishDate(newDate)
                .category("Software Engineering")
                .build();

        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class)))
                .thenReturn(updatedBook);

        Book result = updateBookService.updateBook(bookId, updateRequest);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Clean Code: Second Edition", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
        assertEquals("9780132350885", result.getIsbn());
        assertEquals(newDate, result.getPublishDate());
        assertEquals("Software Engineering", result.getCategory());
    }

    @Test
    void shouldPartiallyUpdateBookSuccessfully() {
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

        Book updateRequest = Book.builder()
                .title("Clean Code: Second Edition")
                .build();

        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Clean Code: Second Edition")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class)))
                .thenReturn(updatedBook);

        Book result = updateBookService.updateBook(bookId, updateRequest);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Clean Code: Second Edition", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
        assertEquals("9780132350884", result.getIsbn());
        assertEquals(publishDate, result.getPublishDate());
        assertEquals("Programming", result.getCategory());
    }

    @Test
    void shouldThrowNotFoundWhenBookDoesNotExist() {
        Long bookId = 1L;
        Book updateRequest = Book.builder()
                .title("Clean Code: Second Edition")
                .build();

        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> updateBookService.updateBook(bookId, updateRequest));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Book not found", exception.getReason());
    }
}

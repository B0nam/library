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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private GetBookService getBookService;

    @Test
    void shouldGetBookByIdSuccessfully() {
        Long bookId = 1L;
        LocalDate publishDate = LocalDate.now();

        Book book = Book.builder()
                .id(bookId)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.of(book));

        Book result = getBookService.getBookById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
        assertEquals("9780132350884", result.getIsbn());
        assertEquals(publishDate, result.getPublishDate());
        assertEquals("Programming", result.getCategory());
    }

    @Test
    void shouldThrowNotFoundWhenBookDoesNotExist() {
        Long bookId = 1L;
        when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> getBookService.getBookById(bookId));
    }
}

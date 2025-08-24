package com.bonam.library.domain.books.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class CreateBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CreateBookService createBookService;

    @Test
    void shouldCreateBookSuccessfully() {
        LocalDate publishDate = LocalDate.now();
        Book bookToCreate = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        Book savedBook = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        when(bookRepository.save(any(Book.class)))
                .thenReturn(savedBook);

        Book result = createBookService.createBook(bookToCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
        assertEquals("9780132350884", result.getIsbn());
        assertEquals(publishDate, result.getPublishDate());
        assertEquals("Programming", result.getCategory());
    }
}

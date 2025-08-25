package com.bonam.library.domain.books.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class GetAllBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private GetAllBookService getAllBookService;

    @Test
    void shouldGetAllBooksSuccessfully() {
        LocalDate publishDate = LocalDate.now();

        Book book1 = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("Design Patterns")
                .author("Gang of Four")
                .isbn("9780201633610")
                .publishDate(publishDate)
                .category("Software Engineering")
                .build();

        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAll())
                .thenReturn(books);

        List<Book> result = getAllBookService.getAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());

        Book firstBook = result.getFirst();
        assertEquals(1L, firstBook.getId());
        assertEquals("Clean Code", firstBook.getTitle());
        assertEquals("Robert C. Martin", firstBook.getAuthor());
        assertEquals("9780132350884", firstBook.getIsbn());
        assertEquals(publishDate, firstBook.getPublishDate());
        assertEquals("Programming", firstBook.getCategory());

        Book secondBook = result.get(1);
        assertEquals(2L, secondBook.getId());
        assertEquals("Design Patterns", secondBook.getTitle());
        assertEquals("Gang of Four", secondBook.getAuthor());
        assertEquals("9780201633610", secondBook.getIsbn());
        assertEquals(publishDate, secondBook.getPublishDate());
        assertEquals("Software Engineering", secondBook.getCategory());
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksExist() {
        when(bookRepository.findAll())
                .thenReturn(List.of());

        List<Book> result = getAllBookService.getAllBooks();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}

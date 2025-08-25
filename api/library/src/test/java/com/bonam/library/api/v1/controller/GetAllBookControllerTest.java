package com.bonam.library.api.v1.controller;

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
import org.springframework.http.HttpStatus;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetAllBookService;

@ExtendWith(MockitoExtension.class)
class GetAllBookControllerTest {

    @Mock
    private GetAllBookService getAllBookService;

    @InjectMocks
    private GetAllBookController controller;

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

        when(getAllBookService.getAllBooks())
                .thenReturn(books);

        var response = controller.getAllBooks();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());

        var firstBook = responseBody.getFirst();
        assertEquals(1L, firstBook.getId());
        assertEquals("Clean Code", firstBook.getTitle());
        assertEquals("Robert C. Martin", firstBook.getAuthor());
        assertEquals("9780132350884", firstBook.getIsbn());
        assertEquals(publishDate, firstBook.getPublishDate());
        assertEquals("Programming", firstBook.getCategory());

        var secondBook = responseBody.get(1);
        assertEquals(2L, secondBook.getId());
        assertEquals("Design Patterns", secondBook.getTitle());
        assertEquals("Gang of Four", secondBook.getAuthor());
        assertEquals("9780201633610", secondBook.getIsbn());
        assertEquals(publishDate, secondBook.getPublishDate());
        assertEquals("Software Engineering", secondBook.getCategory());
    }
}

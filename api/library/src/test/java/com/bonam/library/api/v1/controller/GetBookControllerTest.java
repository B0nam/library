package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;

@ExtendWith(MockitoExtension.class)
class GetBookControllerTest {

    @Mock
    private GetBookService getBookService;

    @InjectMocks
    private GetBookController controller;

    @Test
    void shouldGetBookSuccessfully() {
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

        when(getBookService.getBookById(anyLong()))
                .thenReturn(book);

        var response = controller.getBook(bookId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(bookId, responseBody.getId());
        assertEquals("Clean Code", responseBody.getTitle());
        assertEquals("Robert C. Martin", responseBody.getAuthor());
        assertEquals("9780132350884", responseBody.getIsbn());
        assertEquals(publishDate, responseBody.getPublishDate());
        assertEquals("Programming", responseBody.getCategory());
    }
}

package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.api.v1.model.request.UpdateBookRequestDTO;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.UpdateBookService;

@ExtendWith(MockitoExtension.class)
class UpdateBookControllerTest {

    @Mock
    private UpdateBookService updateBookService;

    @InjectMocks
    private UpdateBookController controller;

    @Test
    void shouldUpdateBookSuccessfully() {
        Long bookId = 1L;
        LocalDate publishDate = LocalDate.now();
        UpdateBookRequestDTO request = UpdateBookRequestDTO.builder()
                .title("Clean Code: Second Edition")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Software Engineering")
                .build();

        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Clean Code: Second Edition")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Software Engineering")
                .build();

        when(updateBookService.updateBook(anyLong(), any(Book.class)))
                .thenReturn(updatedBook);

        var response = controller.updateBook(bookId, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(bookId, responseBody.getId());
        assertEquals("Clean Code: Second Edition", responseBody.getTitle());
        assertEquals("Robert C. Martin", responseBody.getAuthor());
        assertEquals("9780132350884", responseBody.getIsbn());
        assertEquals(publishDate, responseBody.getPublishDate());
        assertEquals("Software Engineering", responseBody.getCategory());
    }
}

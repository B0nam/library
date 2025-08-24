package com.bonam.library.api.v1.controller;

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
import org.springframework.http.HttpStatus;

import com.bonam.library.api.v1.model.request.CreateBookRequestDTO;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.CreateBookService;

@ExtendWith(MockitoExtension.class)
class CreateBookControllerTest {

    @Mock
    private CreateBookService createBookService;

    @InjectMocks
    private CreateBookController controller;

    @Test
    void shouldCreateBookSuccessfully() {
        LocalDate publishDate = LocalDate.now();
        CreateBookRequestDTO request = CreateBookRequestDTO.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        Book createdBook = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        when(createBookService.createBook(any(Book.class)))
                .thenReturn(createdBook);

        var response = controller.createBook(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1L, responseBody.getId());
        assertEquals("Clean Code", responseBody.getTitle());
        assertEquals("Robert C. Martin", responseBody.getAuthor());
        assertEquals("9780132350884", responseBody.getIsbn());
        assertEquals(publishDate, responseBody.getPublishDate());
        assertEquals("Programming", responseBody.getCategory());
    }
}

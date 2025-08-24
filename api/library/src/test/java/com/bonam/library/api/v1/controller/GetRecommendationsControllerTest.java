package com.bonam.library.api.v1.controller;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.recommendation.service.GetBooksRecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRecommendationsControllerTest {

    @Mock
    private GetBooksRecommendationService getBooksRecommendationService;

    @InjectMocks
    private GetRecommendationsController controller;

    @Test
    void shouldReturnRecommendationsSuccessfully() {
        Long userId = 1L;
        LocalDate publishDate = LocalDate.now();

        List<Book> recommendedBooks = List.of(
                Book.builder()
                        .id(1L)
                        .title("Clean Code")
                        .author("Robert C. Martin")
                        .isbn("9780132350884")
                        .publishDate(publishDate)
                        .category("Programming")
                        .build(),
                Book.builder()
                        .id(2L)
                        .title("Design Patterns")
                        .author("Erich Gamma")
                        .isbn("9780201633610")
                        .publishDate(publishDate)
                        .category("Programming")
                        .build()
        );

        when(getBooksRecommendationService.getBooksRecommendations(anyLong()))
                .thenReturn(recommendedBooks);

        var response = controller.getRecommendationsByUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Clean Code", response.getBody().get(0).getTitle());
        assertEquals("Design Patterns", response.getBody().get(1).getTitle());

        verify(getBooksRecommendationService, times(1)).getBooksRecommendations(userId);
    }

    @Test
    void shouldReturnEmptyListWhenNoRecommendationsExist() {
        Long userId = 1L;
        List<Book> emptyRecommendations = new ArrayList<>();

        when(getBooksRecommendationService.getBooksRecommendations(anyLong()))
                .thenReturn(emptyRecommendations);

        var response = controller.getRecommendationsByUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(getBooksRecommendationService, times(1)).getBooksRecommendations(userId);
    }
}

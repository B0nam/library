package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.openapi.controller.GetRecommendationsControllerOpenApi;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.recommendation.service.GetBooksRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class GetRecommendationsController implements GetRecommendationsControllerOpenApi {

    private final GetBooksRecommendationService getBooksRecommendationService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Book>> getRecommendationsByUser(@PathVariable Long id) {
        var recommendations = getBooksRecommendationService.getBooksRecommendations(id);
        return ResponseEntity.ok(recommendations);
    }
}

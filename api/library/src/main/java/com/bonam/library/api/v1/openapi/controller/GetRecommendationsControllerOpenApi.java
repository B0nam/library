package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.domain.books.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Recommendations", description = "API for book recommendations")
public interface GetRecommendationsControllerOpenApi {

    @Operation(summary = "Get book recommendations", description = "Retrieve a list of recommended books based on user preferences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No recommendations found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<Book>> getRecommendationsByUser(Long userId);
}

package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.domain.books.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Google Books", description = "API for interacting with Google Books")
public interface CreateGoogleBookControllerOpenApi {

    @Operation(summary = "Create book by Google book ID", description = "Create a new book in the system using a Google Book ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book created successfully"),
            @ApiResponse(responseCode = "404", description = "Google Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Book> createBookOnGoogleBookId(String googleBookId);
}

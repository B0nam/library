package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.request.CreateBookRequestDTO;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Books", description = "API for book management")
public interface CreateBookControllerOpenApi {

    @Operation(summary = "Create a new book", description = "Creates a new book with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data - validation errors"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ResponseEntity<BookResponseDTO> createBook(CreateBookRequestDTO request);
}

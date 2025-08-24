package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.request.UpdateBookRequestDTO;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Books", description = "API for book management")
public interface UpdateBookControllerOpenApi {

    @Operation(summary = "Update a book", description = "Updates an existing book with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data - validation errors"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<BookResponseDTO> updateBook(Long id, UpdateBookRequestDTO request);
}

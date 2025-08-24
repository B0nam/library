package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.response.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Books", description = "API for book management")
public interface GetBookControllerOpenApi {

    @Operation(summary = "Get a book by ID", description = "Retrieves a book by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<BookResponseDTO> getBook(Long id);
}

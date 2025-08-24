package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.response.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Books", description = "API for book management")
public interface GetAllBookControllerOpenApi {

    @Operation(summary = "List all books", description = "Retrieves all books from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<BookResponseDTO>> getAllBooks();
}

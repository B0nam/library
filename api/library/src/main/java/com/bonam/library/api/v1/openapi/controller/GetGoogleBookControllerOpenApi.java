package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.domain.googlebooks.model.GoogleBookItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Google Books", description = "API for interacting with Google Books")
public interface GetGoogleBookControllerOpenApi {

    @Operation(summary = "Search books by title", description = "Search for books using their title via Google Books API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid title supplied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<GoogleBookItemDTO>> searchBooksByTitle(String title);
}

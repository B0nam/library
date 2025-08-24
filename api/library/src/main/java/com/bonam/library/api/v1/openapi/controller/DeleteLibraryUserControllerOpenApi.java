package com.bonam.library.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Library Users", description = "API for library user management")
public interface DeleteLibraryUserControllerOpenApi {

    @Operation(summary = "Remove library user by ID", description = "Remove a library user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Library user removed successfully"),
            @ApiResponse(responseCode = "404", description = "Library user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<String> removeLibraryUser(Long id);
}

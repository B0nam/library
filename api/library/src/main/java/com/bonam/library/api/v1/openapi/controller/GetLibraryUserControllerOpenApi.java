package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Library Users", description = "API for library user management")
public interface GetLibraryUserControllerOpenApi {

    @Operation(summary = "Get library user by ID", description = "Retrieve a library user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library user retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Library user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<LibraryUserResponseDTO> getLibraryUser(Long id);
}

package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Library Users", description = "API for library user management")
public interface GetAllLibraryUserControllerOpenApi {

    @Operation(summary = "List all library users", description = "Retrieve a list of all library users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<LibraryUserResponseDTO>> getAllLibraryUsers();
}

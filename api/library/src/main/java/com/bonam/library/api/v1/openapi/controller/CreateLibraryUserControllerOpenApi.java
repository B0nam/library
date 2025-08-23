package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.request.CreateLibraryUserRequestDTO;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface CreateLibraryUserControllerOpenApi {

    @Operation(summary = "Create a new library user", description = "Create a new library user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Library user created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data - validation errors"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<LibraryUserResponseDTO> createLibraryUser(
            CreateLibraryUserRequestDTO request);
}

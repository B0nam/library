package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Schema(name = "GetAllLibraryUserController")
public interface GetAllLibraryUserControllerOpenApi {

    @Operation(summary = "Get all library users", description = "Retrieve a list of all library users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<LibraryUserResponseDTO>> getAllLibraryUsers();
}

package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.request.UpdateLibraryUserRequestDTO;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

@Schema(name = "UpdateLibraryUserController")
public interface UpdateLibraryUserControllerOpenApi {

    @Operation(summary = "Update library user name by ID", description = "Update a library user's name by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Library user updated successfully"),
            @ApiResponse(responseCode = "404", description = "Library user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<LibraryUserResponseDTO> updateLibraryUser(Long id, UpdateLibraryUserRequestDTO request);
}

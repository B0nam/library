package com.bonam.library.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "CreateLibraryUserResponseDTO", description = "Response DTO for creating a new library user")
public interface LibraryUserResponseDTOOpenApi {

    @Schema(description = "ID of the library user", example = "1")
    Long getId();

    @Schema(description = "Name of the library user", example = "Daniel Bonam")
    String getName();

    @Schema(description = "Email of the library user", example = "daniiel.bonam@gmail.com")
    String getEmail();

    @Schema(description = "Creation timestamp of the library user", example = "2024-06-15T10:00:00Z")
    LocalDateTime getCreatedAt();

    @Schema(description = "Telephone number of the library user", example = "+5544999999999")
    String getTelephone();
}

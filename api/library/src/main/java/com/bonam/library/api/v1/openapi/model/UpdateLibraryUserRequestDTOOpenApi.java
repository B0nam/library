package com.bonam.library.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UpdateLibraryUserRequestDTO", description = "Request DTO for updating a library user")
public interface UpdateLibraryUserRequestDTOOpenApi {

    @Schema(description = "Name of the library user", example = "Daniel Bonam")
    String getName();

    @Schema(description = "Email of the library user", example = "daniiel.bonam@gmail.com")
    String getEmail();

    @Schema(description = "Telephone number of the library user", example = "+5544999999999")
    String getPhone();
}

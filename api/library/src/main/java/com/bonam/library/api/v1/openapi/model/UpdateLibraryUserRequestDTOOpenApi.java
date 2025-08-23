package com.bonam.library.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UpdateLibraryUserRequestDTO", description = "Request DTO for updating a library user")
public interface UpdateLibraryUserRequestDTOOpenApi {

    String getName();

    String getEmail();

    String getPhone();
}

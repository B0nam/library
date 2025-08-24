package com.bonam.library.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request representation for creating a loan")
public interface CreateLoanRequestDTOOpenApi {

    @Schema(description = "Library user ID", example = "1")
    Long getLibraryUserId();

    @Schema(description = "Book ID", example = "1")
    Long getBookId();
}


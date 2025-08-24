package com.bonam.library.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request representation for updating a loan")
public interface UpdateLoanRequestDTOOpenApi {

    @Schema(description = "Return date", example = "2025-09-20")
    LocalDate getReturnDate();
}

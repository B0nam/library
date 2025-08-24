package com.bonam.library.api.v1.openapi.model;

import com.bonam.library.domain.loans.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Response representation of a loan")
public interface LoanResponseDTOOpenApi {

    @Schema(description = "Loan ID", example = "1")
    Long getId();

    @Schema(description = "Library user ID", example = "1")
    Long getLibraryUserId();

    @Schema(description = "Book ID", example = "1")
    Long getBookId();

    @Schema(description = "Return date", example = "2025-09-20")
    LocalDate getReturnDate();

    @Schema(description = "Creation date", example = "2025-08-23T10:15:30")
    LocalDateTime getLoanDate();

    @Schema(description = "Loan status", example = "PENDING")
    LoanStatus getStatus();
}

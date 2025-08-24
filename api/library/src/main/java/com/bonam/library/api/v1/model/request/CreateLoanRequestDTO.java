package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.CreateLoanRequestDTOOpenApi;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateLoanRequestDTO implements CreateLoanRequestDTOOpenApi {

    @NotNull(message = "Library User ID is required")
    private Long libraryUserId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Expected return date")
    private LocalDate returnDate;
}

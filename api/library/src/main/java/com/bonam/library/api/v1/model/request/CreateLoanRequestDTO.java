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

    @NotNull
    private Long libraryUserId;

    @NotNull
    private Long bookId;

    @NotNull
    private LocalDate returnDate;
}

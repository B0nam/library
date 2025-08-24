package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.UpdateLoanRequestDTOOpenApi;
import com.bonam.library.domain.loans.enums.LoanStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateLoanRequestDTO implements UpdateLoanRequestDTOOpenApi {

    private LocalDate returnDate;

    private LoanStatus status;
}

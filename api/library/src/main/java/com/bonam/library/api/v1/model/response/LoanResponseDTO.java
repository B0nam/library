package com.bonam.library.api.v1.model.response;

import com.bonam.library.api.v1.openapi.model.LoanResponseDTOOpenApi;
import com.bonam.library.domain.loans.enums.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LoanResponseDTO implements LoanResponseDTOOpenApi {

    private Long id;

    private Long libraryUserId;

    private Long bookId;

    private LocalDate returnDate;

    private LocalDateTime loanDate;

    private LoanStatus status;
}

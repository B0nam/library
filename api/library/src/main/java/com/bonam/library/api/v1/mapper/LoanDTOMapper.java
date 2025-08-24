package com.bonam.library.api.v1.mapper;

import com.bonam.library.api.v1.model.request.CreateLoanRequestDTO;
import com.bonam.library.api.v1.model.request.UpdateLoanRequestDTO;
import com.bonam.library.api.v1.model.response.LoanResponseDTO;
import com.bonam.library.domain.loans.model.Loan;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoanDTOMapper {

    public static LoanResponseDTO toResponseDTO(Loan loan) {
        return LoanResponseDTO.builder()
                .id(loan.getId())
                .libraryUserId(loan.getLibraryUser().getId())
                .bookId(loan.getBook().getId())
                .returnDate(loan.getReturnDate())
                .loanDate(loan.getLoanDate())
                .status(loan.getStatus())
                .build();
    }

    public static Loan toEntity(CreateLoanRequestDTO request) {
        return Loan.builder()
                .returnDate(request.getReturnDate())
                .build();
    }

    public static Loan toEntity(UpdateLoanRequestDTO request) {
        return Loan.builder()
                .returnDate(request.getReturnDate())
                .status(request.getStatus())
                .build();
    }
}

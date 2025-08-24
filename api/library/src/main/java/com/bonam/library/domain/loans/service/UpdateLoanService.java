package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateLoanService {

    private final LoanRepository loanRepository;
    private final GetLoanService getLoanService;

    public Loan updateLoan(Long id, Loan updatedLoan) {
        var loan = getLoanService.getLoanById(id);

        loan.setReturnDate(Optional.ofNullable(updatedLoan.getReturnDate())
                .orElse(loan.getReturnDate()));
        loan.setLoanDate(Optional.ofNullable(updatedLoan.getLoanDate())
                .orElse(loan.getLoanDate()));

        return loanRepository.save(loan);
    }
}

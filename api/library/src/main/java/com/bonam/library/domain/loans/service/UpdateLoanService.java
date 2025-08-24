package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateLoanService {

    private final LoanRepository loanRepository;

    public Loan updateLoan(Long id, Loan updatedLoan) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));

        loan.setReturnDate(Optional.ofNullable(updatedLoan.getReturnDate())
                .orElse(loan.getReturnDate()));
        loan.setLoanDate(Optional.ofNullable(updatedLoan.getLoanDate())
                .orElse(loan.getLoanDate()));

        return loanRepository.save(loan);
    }
}

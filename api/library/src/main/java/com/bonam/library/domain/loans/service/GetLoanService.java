package com.bonam.library.domain.loans.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLoanService {

    private final LoanRepository loanRepository;
    private final GetLibraryUserService getLibraryUserService;

    @Transactional(readOnly = true)
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found", id.toString()));
    }

    @Transactional(readOnly = true)
    public List<Loan> getAllLoansByUserId(Long userId) {
        var user = getLibraryUserService.getLibraryUserById(userId);
        return loanRepository.findByLibraryUserId(user.getId());
    }
}

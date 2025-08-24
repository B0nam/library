package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateLoanService {

    private final LoanRepository loanRepository;
    private final GetLibraryUserService getLibraryUserService;
    private final GetBookService getBookService;

    @Transactional
    public Loan createLoan(Loan loan, Long bookId, Long libraryUserId) {

        var libraryUser = getLibraryUserService.getLibraryUserById(libraryUserId);
        var book = getBookService.getBookById(bookId);

        loan.setLibraryUser(libraryUser);
        loan.setBook(book);

        return loanRepository.save(loan);
    }
}

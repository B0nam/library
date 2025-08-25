package com.bonam.library.domain.loans.service;

import com.bonam.library.api.v1.exception.ActiveLoanExistsException;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateLoanService {

    private final GetLibraryUserService getLibraryUserService;
    private final GetBookService getBookService;
    private final GetLoanAvailabilityService getLoanAvailabilityService;
    private final LoanRepository loanRepository;

    @Transactional
    public Loan createLoan(Loan loan, Long bookId, Long libraryUserId) {
        var libraryUser = getLibraryUserService.getLibraryUserById(libraryUserId);
        var book = getBookService.getBookById(bookId);

        if (getLoanAvailabilityService.isActiveLoanForBookId(bookId)) {
            throw new ActiveLoanExistsException(book.getTitle(), bookId.toString());
        }

        loan.setLibraryUser(libraryUser);
        loan.setBook(book);

        return loanRepository.save(loan);
    }
}

package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.loans.enums.LoanStatus;
import com.bonam.library.domain.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetLoanAvailabilityService {

    private final LoanRepository loanRepository;
    private final GetBookService getBookService;

    public boolean isActiveLoanForBookId(Long bookId) {
        var book = getBookService.getBookById(bookId);
        return loanRepository.existsLoanByBookIdAndStatusNot(book.getId(), LoanStatus.RETURNED);
    }
}

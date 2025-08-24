package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.loans.enums.LoanStatus;
import com.bonam.library.domain.loans.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLoanAvailabilityServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private GetBookService getBookService;

    @InjectMocks
    private GetLoanAvailabilityService getLoanAvailabilityService;

    @Test
    void shouldReturnTrueWhenBookHasActiveLoan() {
        var bookId = 1L;
        var book = new Book();
        book.setId(bookId);

        when(getBookService.getBookById(bookId)).thenReturn(book);
        when(loanRepository.existsLoanByBookIdAndStatusNot(bookId, LoanStatus.RETURNED.toString())).thenReturn(true);

        var result = getLoanAvailabilityService.isActiveLoanForBookId(bookId);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenBookHasNoActiveLoan() {
        var bookId = 1L;
        var book = new Book();
        book.setId(bookId);

        when(getBookService.getBookById(bookId)).thenReturn(book);
        when(loanRepository.existsLoanByBookIdAndStatusNot(bookId, LoanStatus.RETURNED.toString())).thenReturn(false);

        var result = getLoanAvailabilityService.isActiveLoanForBookId(bookId);

        assertThat(result).isFalse();
    }
}

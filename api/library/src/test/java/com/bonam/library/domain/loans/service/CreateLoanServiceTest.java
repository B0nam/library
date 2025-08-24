package com.bonam.library.domain.loans.service;

import com.bonam.library.api.v1.exception.ActiveLoanExistsException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private GetLibraryUserService getLibraryUserService;

    @Mock
    private GetBookService getBookService;

    @Mock
    private GetLoanAvailabilityService getLoanAvailabilityService;

    @InjectMocks
    private CreateLoanService createLoanService;

    @Test
    void shouldCreateLoanSuccessfully() {
        var loan = new Loan();
        var bookId = 1L;
        var libraryUserId = 1L;

        var book = new Book();
        book.setId(bookId);

        var libraryUser = new LibraryUser();
        libraryUser.setId(libraryUserId);

        when(getBookService.getBookById(bookId)).thenReturn(book);
        when(getLibraryUserService.getLibraryUserById(libraryUserId)).thenReturn(libraryUser);
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = createLoanService.createLoan(loan, bookId, libraryUserId);

        assertThat(result).isNotNull();
        assertThat(result.getBook()).isEqualTo(book);

        verify(loanRepository).save(loan);
        verify(getBookService).getBookById(bookId);
        verify(getLibraryUserService).getLibraryUserById(libraryUserId);
    }

    @Test
    void shouldThrowExceptionWhenBookHasActiveLoan() {
        var loan = new Loan();
        var bookId = 1L;
        var libraryUserId = 1L;

        var book = new Book();
        book.setId(bookId);

        var libraryUser = new LibraryUser();
        libraryUser.setId(libraryUserId);

        when(getLoanAvailabilityService.isActiveLoanForBookId(bookId)).thenReturn(true);
        when(getBookService.getBookById(bookId)).thenReturn(book);
        when(getLibraryUserService.getLibraryUserById(libraryUserId)).thenReturn(libraryUser);

        assertThatThrownBy(() -> createLoanService.createLoan(loan, bookId, libraryUserId))
                .isInstanceOf(ActiveLoanExistsException.class)
                .hasMessage("Book already has an active loan");
    }
}

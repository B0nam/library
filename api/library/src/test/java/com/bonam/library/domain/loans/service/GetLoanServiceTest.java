package com.bonam.library.domain.loans.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private GetLoanService getLoanService;

    @Test
    void shouldGetLoanByIdSuccessfully() {
        Long loanId = 1L;
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = loanDate.plusDays(14);

        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .build();

        LibraryUser user = LibraryUser.builder()
                .id(1L)
                .name("John Doe")
                .build();

        Loan expectedLoan = Loan.builder()
                .id(loanId)
                .book(book)
                .libraryUser(user)
                .loanDate(loanDate)
                .returnDate(returnDate)
                .build();

        when(loanRepository.findById(loanId))
                .thenReturn(Optional.of(expectedLoan));

        Loan result = getLoanService.getLoanById(loanId);

        assertNotNull(result);
        assertEquals(loanId, result.getId());
        assertEquals(book.getId(), result.getBook().getId());
        assertEquals(user.getId(), result.getLibraryUser().getId());
        assertEquals(loanDate, result.getLoanDate());
        assertEquals(returnDate, result.getReturnDate());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenLoanNotFound() {
        Long loanId = 1L;
        when(loanRepository.findById(loanId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> getLoanService.getLoanById(loanId)
        );
    }
}

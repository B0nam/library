package com.bonam.library.domain.loans.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private GetLibraryUserService getLibraryUserService;

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

    @Test
    void shouldGetAllLoansByUserIdSuccessfully() {
        Long userId = 1L;
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = loanDate.plusDays(14);

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        Book book1 = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("Design Patterns")
                .author("Erich Gamma")
                .build();

        Loan loan1 = Loan.builder()
                .id(1L)
                .book(book1)
                .libraryUser(user)
                .loanDate(loanDate)
                .returnDate(returnDate)
                .build();

        Loan loan2 = Loan.builder()
                .id(2L)
                .book(book2)
                .libraryUser(user)
                .loanDate(loanDate.minusDays(5))
                .returnDate(returnDate.minusDays(5))
                .build();

        List<Loan> expectedLoans = List.of(loan1, loan2);

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(loanRepository.findByLibraryUserId(userId)).thenReturn(expectedLoans);

        List<Loan> result = getLoanService.getAllLoansByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(loan1.getId(), result.get(0).getId());
        assertEquals(loan2.getId(), result.get(1).getId());
        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(loanRepository, times(1)).findByLibraryUserId(userId);
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoLoans() {
        Long userId = 1L;

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        List<Loan> emptyLoans = new ArrayList<>();

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(loanRepository.findByLibraryUserId(userId)).thenReturn(emptyLoans);

        List<Loan> result = getLoanService.getAllLoansByUserId(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(loanRepository, times(1)).findByLibraryUserId(userId);
    }
}

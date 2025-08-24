package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import com.bonam.library.domain.loans.enums.LoanStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.api.v1.model.request.UpdateLoanRequestDTO;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.service.UpdateLoanService;

@ExtendWith(MockitoExtension.class)
class UpdateLoanControllerTest {

    @Mock
    private UpdateLoanService updateLoanService;

    @InjectMocks
    private UpdateLoanController controller;

    @Test
    void shouldUpdateLoanSuccessfully() {
        Long loanId = 1L;
        LocalDate now = LocalDate.now();
        UpdateLoanRequestDTO request = UpdateLoanRequestDTO.builder()
                .returnDate(now.plusDays(14))
                .status(LoanStatus.RETURNED)
                .build();

        LibraryUser user = LibraryUser.builder()
                .id(2L)
                .name("John Doe")
                .build();

        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .build();

        Loan updatedLoan = Loan.builder()
                .id(loanId)
                .book(book)
                .libraryUser(user)
                .loanDate(now)
                .returnDate(now.plusDays(14))
                .status(LoanStatus.RETURNED)
                .build();

        when(updateLoanService.updateLoan(anyLong(), any(Loan.class)))
                .thenReturn(updatedLoan);

        var response = controller.updateLoan(loanId, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(loanId, responseBody.getId());
        assertEquals(book.getId(), responseBody.getBookId());
        assertEquals(user.getId(), responseBody.getLibraryUserId());
        assertEquals(now, responseBody.getLoanDate());
        assertEquals(now.plusDays(14), responseBody.getReturnDate());
        assertEquals(LoanStatus.RETURNED, responseBody.getStatus());
    }
}

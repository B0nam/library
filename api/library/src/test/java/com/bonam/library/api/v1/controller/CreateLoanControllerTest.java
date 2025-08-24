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

import com.bonam.library.api.v1.model.request.CreateLoanRequestDTO;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.service.CreateLoanService;

@ExtendWith(MockitoExtension.class)
class CreateLoanControllerTest {

    @Mock
    private CreateLoanService createLoanService;

    @InjectMocks
    private CreateLoanController controller;

    @Test
    void shouldCreateLoanSuccessfully() {
        LocalDate now = LocalDate.now();
        CreateLoanRequestDTO request = CreateLoanRequestDTO.builder()
                .bookId(1L)
                .libraryUserId(2L)
                .returnDate(now.plusDays(7))
                .build();

        LibraryUser user = LibraryUser.builder()
                .id(2L)
                .name("John Doe")
                .build();

        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .build();

        Loan createdLoan = Loan.builder()
                .id(1L)
                .book(book)
                .libraryUser(user)
                .loanDate(now)
                .returnDate(now.plusDays(7))
                .status(LoanStatus.RETURNED)
                .build();

        when(createLoanService.createLoan(any(Loan.class), anyLong(), anyLong()))
                .thenReturn(createdLoan);

        var response = controller.createLoan(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1L, responseBody.getId());
        assertEquals(1L, responseBody.getBookId());
        assertEquals(2L, responseBody.getLibraryUserId());
        assertEquals(now, responseBody.getLoanDate());
        assertEquals(now.plusDays(7), responseBody.getReturnDate());
        assertEquals(LoanStatus.RETURNED, responseBody.getStatus());
    }
}

package com.bonam.library.api.v1.mapper;

import com.bonam.library.api.v1.model.request.CreateLoanRequestDTO;
import com.bonam.library.api.v1.model.request.UpdateLoanRequestDTO;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.loans.enums.LoanStatus;
import com.bonam.library.domain.loans.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class LoanDTOMapperTest {

    @Test
    void shouldMapLoanToResponseDTO() {
        LibraryUser user = LibraryUser.builder()
                .id(1L)
                .build();

        Book book = Book.builder()
                .id(2L)
                .build();

        LocalDate now = LocalDate.now();

        Loan loan = Loan.builder()
                .id(3L)
                .libraryUser(user)
                .book(book)
                .loanDate(now)
                .returnDate(now.plusDays(7))
                .status(LoanStatus.PENDING)
                .build();

        var response = LoanDTOMapper.toResponseDTO(loan);

        assertNotNull(response);
        assertEquals(3L, response.getId());
        assertEquals(1L, response.getLibraryUserId());
        assertEquals(2L, response.getBookId());
        assertEquals(now, response.getLoanDate());
        assertEquals(now.plusDays(7), response.getReturnDate());
        assertEquals(LoanStatus.PENDING, response.getStatus());
    }

    @Test
    void shouldMapCreateRequestDTOToEntity() {
        LocalDate returnDate = LocalDate.now().plusDays(7);
        CreateLoanRequestDTO request = CreateLoanRequestDTO.builder()
                .returnDate(returnDate)
                .build();

        var entity = LoanDTOMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(returnDate, entity.getReturnDate());
    }

    @Test
    void shouldMapUpdateRequestDTOToEntity() {
        LocalDate returnDate = LocalDate.now().plusDays(7);
        UpdateLoanRequestDTO request = UpdateLoanRequestDTO.builder()
                .returnDate(returnDate)
                .status(LoanStatus.RETURNED)
                .build();

        var entity = LoanDTOMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(returnDate, entity.getReturnDate());
        assertEquals(LoanStatus.RETURNED, entity.getStatus());
    }
}

package com.bonam.library.domain.loans.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.loans.model.Loan;
import com.bonam.library.domain.loans.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateLoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private GetLoanService getLoanService;

    @InjectMocks
    private UpdateLoanService updateLoanService;

    @Test
    void shouldUpdateLoanSuccessfully() {
        var id = 1L;
        var existingLoan = new Loan();
        existingLoan.setId(id);
        existingLoan.setLoanDate(LocalDate.of(2025, 8, 17));
        existingLoan.setReturnDate(null);

        var updatedLoan = new Loan();
        var newReturnDate = LocalDate.of(2025, 8, 24);
        updatedLoan.setReturnDate(newReturnDate);

        when(getLoanService.getLoanById(id)).thenReturn(existingLoan);
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateLoanService.updateLoan(id, updatedLoan);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getReturnDate()).isEqualTo(newReturnDate);
        assertThat(result.getLoanDate()).isEqualTo(existingLoan.getLoanDate());

        verify(getLoanService).getLoanById(id);
        verify(loanRepository).save(existingLoan);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenLoanDoesNotExist() {
        var id = 1L;
        var updatedLoan = new Loan();

        when(getLoanService.getLoanById(id))
                .thenThrow(new ResourceNotFoundException("Loan not found", String.valueOf(id)));

        assertThrows(ResourceNotFoundException.class, () ->
                updateLoanService.updateLoan(id, updatedLoan)
        );
    }

    @Test
    void shouldKeepExistingValuesWhenUpdatedValuesAreNull() {
        var id = 1L;
        var existingLoan = new Loan();
        existingLoan.setId(id);
        var existingLoanDate = LocalDate.of(2025, 8, 17);
        var existingReturnDate = LocalDate.of(2025, 8, 24);
        existingLoan.setLoanDate(existingLoanDate);
        existingLoan.setReturnDate(existingReturnDate);

        var updatedLoan = new Loan();

        when(getLoanService.getLoanById(id)).thenReturn(existingLoan);
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateLoanService.updateLoan(id, updatedLoan);

        assertThat(result).isNotNull();
        assertThat(result.getReturnDate()).isEqualTo(existingReturnDate);
    }
}

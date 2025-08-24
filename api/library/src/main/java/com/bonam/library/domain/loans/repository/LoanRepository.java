package com.bonam.library.domain.loans.repository;

import com.bonam.library.domain.loans.enums.LoanStatus;
import com.bonam.library.domain.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsLoanByBookIdAndStatusNot(Long id, LoanStatus loanStatus);
}

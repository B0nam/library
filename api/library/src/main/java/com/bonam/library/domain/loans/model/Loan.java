package com.bonam.library.domain.loans.model;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.loans.enums.LoanStatus;
import com.bonam.library.domain.users.model.LibraryUser;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "library_user_id", nullable = false)
    private LibraryUser libraryUser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull
    private LocalDateTime loanDate;

    @NotNull
    private LocalDateTime returnDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.PENDING;

    @PrePersist
    public void setLoanDate() {
        this.loanDate = LocalDateTime.now();
    }
}

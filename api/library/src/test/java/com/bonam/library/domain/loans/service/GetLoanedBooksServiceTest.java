package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLoanedBooksServiceTest {

    @Mock
    private GetLoanService getLoanService;

    @Mock
    private GetBookService getBookService;

    @Mock
    private GetLibraryUserService getLibraryUserService;

    @InjectMocks
    private GetLoanedBooksService getLoanedBooksService;

    @Test
    void shouldReturnLoanedBooksWhenUserHasLoans() {
        Long userId = 1L;

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        Book book1 = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(LocalDate.now())
                .category("Programming")
                .build();

        Book book2 = Book.builder()
                .id(2L)
                .title("Design Patterns")
                .author("Erich Gamma")
                .isbn("9780201633610")
                .publishDate(LocalDate.now())
                .category("Programming")
                .build();

        Loan loan1 = Loan.builder()
                .id(1L)
                .book(book1)
                .libraryUser(user)
                .loanDate(LocalDate.now().minusDays(10))
                .returnDate(LocalDate.now().plusDays(10))
                .build();

        Loan loan2 = Loan.builder()
                .id(2L)
                .book(book2)
                .libraryUser(user)
                .loanDate(LocalDate.now().minusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .build();

        List<Loan> loans = List.of(loan1, loan2);

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(getLoanService.getAllLoansByUserId(userId)).thenReturn(loans);
        when(getBookService.getBookById(1L)).thenReturn(book1);
        when(getBookService.getBookById(2L)).thenReturn(book2);

        List<Book> result = getLoanedBooksService.getLoanedBooksByUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
        assertEquals("Design Patterns", result.get(1).getTitle());

        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(getLoanService, times(1)).getAllLoansByUserId(userId);
        verify(getBookService, times(1)).getBookById(1L);
        verify(getBookService, times(1)).getBookById(2L);
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoLoans() {
        Long userId = 1L;

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        List<Loan> emptyLoans = Collections.emptyList();

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(getLoanService.getAllLoansByUserId(userId)).thenReturn(emptyLoans);

        List<Book> result = getLoanedBooksService.getLoanedBooksByUser(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(getLoanService, times(1)).getAllLoansByUserId(userId);
        verify(getBookService, times(0)).getBookById(anyLong());
    }
}

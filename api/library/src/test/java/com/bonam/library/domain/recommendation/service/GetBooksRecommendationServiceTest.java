package com.bonam.library.domain.recommendation.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.service.GetLoanedBooksService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBooksRecommendationServiceTest {

    @Mock
    private GetBookService getBookService;

    @Mock
    private GetLibraryUserService getLibraryUserService;

    @Mock
    private GetLoanedBooksService getLoanedBooksService;

    @InjectMocks
    private GetBooksRecommendationService getBooksRecommendationService;

    @Test
    void shouldReturnEmptyListWhenUserHasNoLoanedBooks() {
        Long userId = 1L;

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        List<Book> emptyLoanedBooks = Collections.emptyList();

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(getLoanedBooksService.getLoanedBooksByUser(userId)).thenReturn(emptyLoanedBooks);

        List<Book> result = getBooksRecommendationService.getBooksRecommendations(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(getLoanedBooksService, times(1)).getLoanedBooksByUser(userId);
        verifyNoInteractions(getBookService);
    }

    @Test
    void shouldReturnRecommendationsBasedOnUserLoanedBooksCategories() {
        Long userId = 1L;
        LocalDate publishDate = LocalDate.now();

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        Book loanedBook1 = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        Book loanedBook2 = Book.builder()
                .id(2L)
                .title("Domain-Driven Design")
                .author("Eric Evans")
                .isbn("9780321125217")
                .publishDate(publishDate)
                .category("Software Design")
                .build();

        List<Book> loanedBooks = List.of(loanedBook1, loanedBook2);

        Book recommendableBook1 = Book.builder()
                .id(3L)
                .title("Refactoring")
                .author("Martin Fowler")
                .isbn("9780201485677")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        Book recommendableBook2 = Book.builder()
                .id(4L)
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("9780134685991")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        List<Book> programmingBooks = List.of(loanedBook1, recommendableBook1, recommendableBook2);

        Book recommendableBook3 = Book.builder()
                .id(5L)
                .title("Patterns of Enterprise Application Architecture")
                .author("Martin Fowler")
                .isbn("9780321127426")
                .publishDate(publishDate)
                .category("Software Design")
                .build();

        List<Book> softwareDesignBooks = List.of(loanedBook2, recommendableBook3);

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(getLoanedBooksService.getLoanedBooksByUser(userId)).thenReturn(loanedBooks);
        when(getBookService.getBooksByCategory("Programming")).thenReturn(programmingBooks);
        when(getBookService.getBooksByCategory("Software Design")).thenReturn(softwareDesignBooks);

        List<Book> result = getBooksRecommendationService.getBooksRecommendations(userId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(recommendableBook1));
        assertTrue(result.contains(recommendableBook2));
        assertTrue(result.contains(recommendableBook3));
        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(getLoanedBooksService, times(1)).getLoanedBooksByUser(userId);
        verify(getBookService, times(1)).getBooksByCategory("Programming");
        verify(getBookService, times(1)).getBooksByCategory("Software Design");
    }

    @Test
    void shouldNotRecommendBooksAlreadyLoaned() {
        Long userId = 1L;
        LocalDate publishDate = LocalDate.now();

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        Book loanedBook = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        List<Book> loanedBooks = List.of(loanedBook);

        List<Book> programmingBooks = List.of(loanedBook);

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(getLoanedBooksService.getLoanedBooksByUser(userId)).thenReturn(loanedBooks);
        when(getBookService.getBooksByCategory("Programming")).thenReturn(programmingBooks);

        List<Book> result = getBooksRecommendationService.getBooksRecommendations(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(getLoanedBooksService, times(1)).getLoanedBooksByUser(userId);
        verify(getBookService, times(1)).getBooksByCategory("Programming");
    }

    @Test
    void shouldNotAddDuplicateRecommendations() {
        Long userId = 1L;
        LocalDate publishDate = LocalDate.now();

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        Book loanedBook1 = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        Book loanedBook2 = Book.builder()
                .id(2L)
                .title("Design Patterns")
                .author("Erich Gamma")
                .isbn("9780201633610")
                .publishDate(publishDate)
                .category("Software Design")
                .build();

        List<Book> loanedBooks = List.of(loanedBook1, loanedBook2);

        Book multiCategoryBook = Book.builder()
                .id(3L)
                .title("Refactoring")
                .author("Martin Fowler")
                .isbn("9780201485677")
                .publishDate(publishDate)
                .category("Both")
                .build();

        List<Book> programmingBooks = List.of(loanedBook1, multiCategoryBook);

        List<Book> softwareDesignBooks = List.of(loanedBook2, multiCategoryBook);

        when(getLibraryUserService.getLibraryUserById(userId)).thenReturn(user);
        when(getLoanedBooksService.getLoanedBooksByUser(userId)).thenReturn(loanedBooks);
        when(getBookService.getBooksByCategory("Programming")).thenReturn(programmingBooks);
        when(getBookService.getBooksByCategory("Software Design")).thenReturn(softwareDesignBooks);

        List<Book> result = getBooksRecommendationService.getBooksRecommendations(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(multiCategoryBook.getId(), result.getFirst().getId());
        verify(getLibraryUserService, times(1)).getLibraryUserById(userId);
        verify(getLoanedBooksService, times(1)).getLoanedBooksByUser(userId);
        verify(getBookService, times(1)).getBooksByCategory("Programming");
        verify(getBookService, times(1)).getBooksByCategory("Software Design");
    }
}

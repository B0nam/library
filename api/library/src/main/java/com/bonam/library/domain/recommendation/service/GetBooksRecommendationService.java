package com.bonam.library.domain.recommendation.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import com.bonam.library.domain.loans.service.GetLoanedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBooksRecommendationService {

    private final GetBookService getBookService;
    private final GetLibraryUserService getLibraryUserService;
    private final GetLoanedBooksService getLoanedBooksService;

    @Transactional(readOnly = true)
    public List<Book> getBooksRecommendations(Long userId) {

        var user = getLibraryUserService.getLibraryUserById(userId);
        var loanedBooks = getLoanedBooksService.getLoanedBooksByUser(user.getId());

        if (loanedBooks.isEmpty()) {
            return new ArrayList<>();
        }

        var userCategories = new ArrayList<String>();
        loanedBooks.forEach(book -> {
            if (!userCategories.contains(book.getCategory())) {
                userCategories.add(book.getCategory());
            }
        });

        var loanedBookIds = loanedBooks.stream()
                .map(Book::getId)
                .toList();

        var recommendedBooks = new ArrayList<Book>();
        userCategories.forEach(category -> {
            var booksInCategory = getBookService.getBooksByCategory(category);

            booksInCategory.forEach(book -> {
                if (!loanedBookIds.contains(book.getId()) && !recommendedBooks.contains(book)) {
                    recommendedBooks.add(book);
                }
            });
        });

        return recommendedBooks;
    }
}

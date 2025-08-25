package com.bonam.library.domain.loans.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.GetBookService;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLoanedBooksService {

    private final GetLoanService getLoanService;
    private final GetBookService getBookService;
    private final GetLibraryUserService getLibraryUserService;

    @Transactional(readOnly = true)
    public List<Book> getLoanedBooksByUser(Long userId) {
        List<Book> books = new ArrayList<>();
        var user = getLibraryUserService.getLibraryUserById(userId);
        var loans = getLoanService.getAllLoansByUserId(user.getId());
        loans.forEach(loan -> books.add(getBookService.getBookById(loan.getBook().getId())));
        return books;
    }
}

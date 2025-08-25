package com.bonam.library.domain.books.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
}

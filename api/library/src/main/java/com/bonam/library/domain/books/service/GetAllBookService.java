package com.bonam.library.domain.books.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllBookService {

    private final BookRepository bookRepository;

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

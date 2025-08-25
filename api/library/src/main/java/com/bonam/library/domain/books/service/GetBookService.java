package com.bonam.library.domain.books.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found", id.toString()));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findAllByCategory(category);
    }
}

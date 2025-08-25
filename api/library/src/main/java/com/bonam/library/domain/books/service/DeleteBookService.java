package com.bonam.library.domain.books.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.books.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookService {

    private final BookRepository bookRepository;

    @Transactional
    public void deleteBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found", id.toString()));

        bookRepository.delete(book);
    }
}

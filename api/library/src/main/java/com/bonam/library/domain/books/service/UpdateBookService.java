package com.bonam.library.domain.books.service;

import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateBookService {

    private final GetBookService getBookService;
    private final BookRepository bookRepository;

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        var book = getBookService.getBookById(id);

        book.setTitle(Optional.ofNullable(updatedBook.getTitle()).orElse(book.getTitle()));
        book.setAuthor(Optional.ofNullable(updatedBook.getAuthor()).orElse(book.getAuthor()));
        book.setIsbn(Optional.ofNullable(updatedBook.getIsbn()).orElse(book.getIsbn()));
        book.setPublishDate(Optional.ofNullable(updatedBook.getPublishDate()).orElse(book.getPublishDate()));
        book.setCategory(Optional.ofNullable(updatedBook.getCategory()).orElse(book.getCategory()));

        return bookRepository.save(book);
    }
}

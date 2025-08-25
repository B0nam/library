package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.openapi.controller.CreateGoogleBookControllerOpenApi;
import com.bonam.library.domain.books.model.Book;
import com.bonam.library.domain.books.service.CreateBookService;
import com.bonam.library.domain.googlebooks.service.GetGoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/google-books")
@RequiredArgsConstructor
public class CreateGoogleBookController implements CreateGoogleBookControllerOpenApi {

    private final GetGoogleBooksService getGoogleBooksService;
    private final CreateBookService createBookService;

    @PostMapping("/create/{googleBookId}")
    public ResponseEntity<Book> createBookOnGoogleBookId(@PathVariable String googleBookId) {

        Book googleBook = getGoogleBooksService.getBookById(googleBookId);
        Book book = createBookService.createBook(googleBook);

        return ResponseEntity.ok(book);
    }
}

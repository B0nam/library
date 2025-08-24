package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.BookDTOMapper;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import com.bonam.library.api.v1.openapi.controller.GetAllBookControllerOpenApi;
import com.bonam.library.domain.books.service.GetAllBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class GetAllBookController implements GetAllBookControllerOpenApi {

    private final GetAllBookService getAllBookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        var books = getAllBookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(BookDTOMapper.toResponseDTOList(books));
    }
}

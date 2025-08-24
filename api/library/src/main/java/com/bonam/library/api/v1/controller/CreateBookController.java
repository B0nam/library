package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.BookDTOMapper;
import com.bonam.library.api.v1.model.request.CreateBookRequestDTO;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import com.bonam.library.api.v1.openapi.controller.CreateBookControllerOpenApi;
import com.bonam.library.domain.books.service.CreateBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class CreateBookController implements CreateBookControllerOpenApi {

    private final CreateBookService createBookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid CreateBookRequestDTO request) {
        var book = BookDTOMapper.toEntity(request);
        var createdBook = createBookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookDTOMapper.toResponseDTO(createdBook));
    }
}

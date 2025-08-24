package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.BookDTOMapper;
import com.bonam.library.api.v1.model.request.UpdateBookRequestDTO;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import com.bonam.library.api.v1.openapi.controller.UpdateBookControllerOpenApi;
import com.bonam.library.domain.books.service.UpdateBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class UpdateBookController implements UpdateBookControllerOpenApi {

    private final UpdateBookService updateBookService;

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookRequestDTO request) {
        var book = BookDTOMapper.toEntity(request);
        var updatedBook = updateBookService.updateBook(id, book);
        return ResponseEntity.status(HttpStatus.OK).body(BookDTOMapper.toResponseDTO(updatedBook));
    }
}

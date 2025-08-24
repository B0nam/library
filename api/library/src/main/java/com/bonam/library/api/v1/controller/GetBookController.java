package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.BookDTOMapper;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import com.bonam.library.api.v1.openapi.controller.GetBookControllerOpenApi;
import com.bonam.library.domain.books.service.GetBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class GetBookController implements GetBookControllerOpenApi {

    private final GetBookService getBookService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        var book = getBookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(BookDTOMapper.toResponseDTO(book));
    }
}

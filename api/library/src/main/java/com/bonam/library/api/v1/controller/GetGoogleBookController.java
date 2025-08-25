package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.openapi.controller.GetGoogleBookControllerOpenApi;
import com.bonam.library.domain.googlebooks.model.GoogleBookItemDTO;
import com.bonam.library.domain.googlebooks.service.GetGoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/google-books")
@RequiredArgsConstructor
public class GetGoogleBookController implements GetGoogleBookControllerOpenApi {

    private final GetGoogleBooksService googleBooksService;

    @GetMapping("/search/{title}")
    public ResponseEntity<List<GoogleBookItemDTO>> searchBooksByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body(googleBooksService.searchBooksByTitle(title));
    }
}

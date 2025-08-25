package com.bonam.library.api.v1.model.response;

import com.bonam.library.api.v1.openapi.model.BookResponseDTOOpenApi;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookResponseDTO implements BookResponseDTOOpenApi {

    private Long id;

    private String title;

    private String author;

    private String isbn;

    private LocalDate publishDate;

    private String category;
}

package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.UpdateBookRequestDTOOpenApi;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateBookRequestDTO implements UpdateBookRequestDTOOpenApi {

    private String title;

    private String author;

    private String isbn;

    private LocalDate publishDate;

    private String category;
}

package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.CreateBookRequestDTOOpenApi;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateBookRequestDTO implements CreateBookRequestDTOOpenApi {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    private LocalDate publishDate;

    @NotNull
    private String category;
}

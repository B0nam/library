package com.bonam.library.api.v1.model.response;

import com.bonam.library.api.v1.openapi.model.BookResponseDTOOpenApi;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BookResponseDTO implements BookResponseDTOOpenApi {

    @NotNull
    private Long id;

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

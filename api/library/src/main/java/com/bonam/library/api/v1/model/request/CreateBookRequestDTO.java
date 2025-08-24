package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.CreateBookRequestDTOOpenApi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreateBookRequestDTO implements CreateBookRequestDTOOpenApi {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Publish date is required")
    private LocalDate publishDate;

    @NotBlank(message = "Category is required")
    private String category;
}

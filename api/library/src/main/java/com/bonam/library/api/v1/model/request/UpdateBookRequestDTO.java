package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.UpdateBookRequestDTOOpenApi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateBookRequestDTO implements UpdateBookRequestDTOOpenApi {

    @NotBlank(message = "The title cannot be blank")
    private String title;

    @NotBlank(message = "The author cannot be blank")
    private String author;

    @NotBlank(message = "The ISBN cannot be blank")
    private String isbn;

    @NotNull(message = "The publish date cannot be null")
    private LocalDate publishDate;

    @NotBlank(message = "The category cannot be blank")
    private String category;
}

package com.bonam.library.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Request representation for updating a book")
public interface UpdateBookRequestDTOOpenApi {

    @Schema(description = "Book title", example = "The Lord of the Rings")
    String getTitle();

    @Schema(description = "Book author", example = "J.R.R. Tolkien")
    String getAuthor();

    @Schema(description = "Book ISBN", example = "9788533613379")
    String getIsbn();

    @Schema(description = "Publication date", example = "2023-08-23")
    LocalDate getPublishDate();

    @Schema(description = "Book category", example = "Fantasy")
    String getCategory();
}

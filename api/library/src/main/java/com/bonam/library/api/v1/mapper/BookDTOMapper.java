package com.bonam.library.api.v1.mapper;

import com.bonam.library.api.v1.model.request.CreateBookRequestDTO;
import com.bonam.library.api.v1.model.request.UpdateBookRequestDTO;
import com.bonam.library.api.v1.model.response.BookResponseDTO;
import com.bonam.library.domain.books.model.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class BookDTOMapper {

    public static BookResponseDTO toResponseDTO(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .category(book.getCategory())
                .build();
    }

    public static Book toEntity(CreateBookRequestDTO request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publishDate(request.getPublishDate())
                .category(request.getCategory())
                .build();
    }

    public static Book toEntity(UpdateBookRequestDTO request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publishDate(request.getPublishDate())
                .category(request.getCategory())
                .build();
    }

    public static List<BookResponseDTO> toResponseDTOList(List<Book> books) {
        return books.stream()
                .map(BookDTOMapper::toResponseDTO)
                .toList();
    }
}

package com.bonam.library.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonam.library.api.v1.model.request.CreateBookRequestDTO;
import com.bonam.library.api.v1.model.request.UpdateBookRequestDTO;
import com.bonam.library.domain.books.model.Book;

@ExtendWith(MockitoExtension.class)
class BookDTOMapperTest {

    @Test
    void shouldMapBookToResponseDTO() {
        LocalDate publishDate = LocalDate.now();
        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publishDate(publishDate)
                .category("Programming")
                .build();

        var response = BookDTOMapper.toResponseDTO(book);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Clean Code", response.getTitle());
        assertEquals("Robert C. Martin", response.getAuthor());
        assertEquals("9780132350884", response.getIsbn());
        assertEquals(publishDate, response.getPublishDate());
        assertEquals("Programming", response.getCategory());
    }

    @Test
    void shouldMapCreateRequestDTOToEntity() {
        LocalDate publishDate = LocalDate.now();
        CreateBookRequestDTO request = CreateBookRequestDTO.builder()
                .title("Design Patterns")
                .author("Gang of Four")
                .isbn("9780201633610")
                .publishDate(publishDate)
                .category("Software Engineering")
                .build();

        var entity = BookDTOMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("Design Patterns", entity.getTitle());
        assertEquals("Gang of Four", entity.getAuthor());
        assertEquals("9780201633610", entity.getIsbn());
        assertEquals(publishDate, entity.getPublishDate());
        assertEquals("Software Engineering", entity.getCategory());
    }

    @Test
    void shouldMapUpdateRequestDTOToEntity() {
        LocalDate publishDate = LocalDate.now();
        UpdateBookRequestDTO request = UpdateBookRequestDTO.builder()
                .title("Updated Title")
                .author("Updated Author")
                .isbn("1234567890")
                .publishDate(publishDate)
                .category("Updated Category")
                .build();

        var entity = BookDTOMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("Updated Title", entity.getTitle());
        assertEquals("Updated Author", entity.getAuthor());
        assertEquals("1234567890", entity.getIsbn());
        assertEquals(publishDate, entity.getPublishDate());
        assertEquals("Updated Category", entity.getCategory());
    }
}

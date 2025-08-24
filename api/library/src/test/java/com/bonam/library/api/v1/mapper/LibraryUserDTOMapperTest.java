package com.bonam.library.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonam.library.api.v1.model.request.CreateLibraryUserRequestDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;

@ExtendWith(MockitoExtension.class)
class LibraryUserDTOMapperTest {

    @Test
    void shouldMapCreateRequestDTOToEntity() {
        CreateLibraryUserRequestDTO request = CreateLibraryUserRequestDTO.builder()
                .name("John Doe")
                .email("john@example.com")
                .telephone("123456789")
                .build();

        var entity = LibraryUserDTOMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("John Doe", entity.getName());
        assertEquals("john@example.com", entity.getEmail());
        assertEquals("123456789", entity.getPhone());
    }

    @Test
    void shouldMapLibraryUserToResponseDTO() {
        LocalDateTime now = LocalDateTime.now();
        LibraryUser user = LibraryUser.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        var response = LibraryUserDTOMapper.toResponseDTO(user);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("123456789", response.getPhone());
        assertEquals(now, response.getCreatedAt());
    }

    @Test
    void shouldMapLibraryUserListToResponseDTOList() {
        LocalDateTime now = LocalDateTime.now();
        LibraryUser user1 = LibraryUser.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        LibraryUser user2 = LibraryUser.builder()
                .id(2L)
                .name("Jane Doe")
                .email("jane@example.com")
                .phone("987654321")
                .createdAt(now)
                .build();

        List<LibraryUser> users = List.of(user1, user2);

        var responses = LibraryUserDTOMapper.toResponseDTOList(users);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        var response1 = responses.getFirst();
        assertEquals(1L, response1.getId());
        assertEquals("John Doe", response1.getName());
        assertEquals("john@example.com", response1.getEmail());
        assertEquals("123456789", response1.getPhone());
        assertEquals(now, response1.getCreatedAt());

        var response2 = responses.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("Jane Doe", response2.getName());
        assertEquals("jane@example.com", response2.getEmail());
        assertEquals("987654321", response2.getPhone());
        assertEquals(now, response2.getCreatedAt());
    }
}

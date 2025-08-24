package com.bonam.library.domain.libraryusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.bonam.library.api.v1.model.request.UpdateLibraryUserRequestDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;

@ExtendWith(MockitoExtension.class)
class UpdateLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private UpdateLibraryUserService updateLibraryUserService;

    @Test
    void shouldUpdateLibraryUserSuccessfully() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

        LibraryUser existingUser = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        UpdateLibraryUserRequestDTO request = UpdateLibraryUserRequestDTO.builder()
                .name("John Doe Updated")
                .email("john.updated@example.com")
                .phone("987654321")
                .build();

        LibraryUser updatedUser = LibraryUser.builder()
                .id(userId)
                .name("John Doe Updated")
                .email("john.updated@example.com")
                .phone("987654321")
                .createdAt(now)
                .build();

        when(libraryUserRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingUser));
        when(libraryUserRepository.save(any(LibraryUser.class)))
                .thenReturn(updatedUser);

        LibraryUser result = updateLibraryUserService.updateLibraryUser(userId, request);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe Updated", result.getName());
        assertEquals("john.updated@example.com", result.getEmail());
        assertEquals("987654321", result.getPhone());
        assertEquals(now, result.getCreatedAt());
    }

    @Test
    void shouldPartiallyUpdateLibraryUserSuccessfully() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

        LibraryUser existingUser = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        UpdateLibraryUserRequestDTO request = UpdateLibraryUserRequestDTO.builder()
                .name("John Doe Updated")
                .build();

        LibraryUser updatedUser = LibraryUser.builder()
                .id(userId)
                .name("John Doe Updated")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        when(libraryUserRepository.findById(anyLong()))
                .thenReturn(Optional.of(existingUser));
        when(libraryUserRepository.save(any(LibraryUser.class)))
                .thenReturn(updatedUser);

        LibraryUser result = updateLibraryUserService.updateLibraryUser(userId, request);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe Updated", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("123456789", result.getPhone());
        assertEquals(now, result.getCreatedAt());
    }

    @Test
    void shouldThrowNotFoundWhenUserDoesNotExist() {
        Long userId = 1L;
        UpdateLibraryUserRequestDTO request = UpdateLibraryUserRequestDTO.builder()
                .name("John Doe Updated")
                .build();

        when(libraryUserRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> updateLibraryUserService.updateLibraryUser(userId, request));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Library user not found", exception.getReason());
    }
}

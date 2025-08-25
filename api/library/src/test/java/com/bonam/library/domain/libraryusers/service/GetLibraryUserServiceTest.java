package com.bonam.library.domain.libraryusers.service;

import com.bonam.library.api.v1.exception.ResourceNotFoundException;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private GetLibraryUserService getLibraryUserService;

    @Test
    void shouldGetLibraryUserByIdSuccessfully() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        when(libraryUserRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        LibraryUser result = getLibraryUserService.getLibraryUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("123456789", result.getPhone());
        assertEquals(now, result.getCreatedAt());
    }

    @Test
    void shouldThrowNotFoundWhenUserDoesNotExist() {
        Long userId = 1L;
        when(libraryUserRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> getLibraryUserService.getLibraryUserById(userId));
    }
}

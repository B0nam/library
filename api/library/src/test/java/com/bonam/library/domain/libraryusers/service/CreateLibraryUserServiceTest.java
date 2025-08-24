package com.bonam.library.domain.libraryusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;

@ExtendWith(MockitoExtension.class)
class CreateLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private CreateLibraryUserService createLibraryUserService;

    @Test
    void shouldCreateLibraryUserSuccessfully() {
        LocalDateTime now = LocalDateTime.now();
        LibraryUser userToCreate = LibraryUser.builder()
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .build();

        LibraryUser savedUser = LibraryUser.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        when(libraryUserRepository.save(any(LibraryUser.class)))
                .thenReturn(savedUser);

        LibraryUser result = createLibraryUserService.createLibraryUser(userToCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("123456789", result.getPhone());
        assertEquals(now, result.getCreatedAt());
    }
}

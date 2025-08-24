package com.bonam.library.domain.libraryusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
class DeleteLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @Mock
    private GetLibraryUserService getLibraryUserService;

    @InjectMocks
    private DeleteLibraryUserService deleteLibraryUserService;

    @Test
    void shouldDeleteLibraryUserSuccessfully() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

        LibraryUser existingUser = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        when(getLibraryUserService.getLibraryUserById(anyLong()))
                .thenReturn(existingUser);
        doNothing().when(libraryUserRepository).deleteById(anyLong());

        deleteLibraryUserService.removeLibraryUserById(userId);

        verify(getLibraryUserService).getLibraryUserById(userId);
        verify(libraryUserRepository).deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;
        when(getLibraryUserService.getLibraryUserById(anyLong()))
                .thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> deleteLibraryUserService.removeLibraryUserById(userId));

        assertEquals("Library user not found with id: " + userId, exception.getMessage());
    }
}

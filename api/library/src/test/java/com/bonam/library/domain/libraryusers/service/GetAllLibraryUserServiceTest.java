package com.bonam.library.domain.libraryusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;

@ExtendWith(MockitoExtension.class)
class GetAllLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private GetAllLibraryUserService getAllLibraryUserService;

    @Test
    void shouldGetAllLibraryUsersSuccessfully() {
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

        when(libraryUserRepository.findAll())
                .thenReturn(users);

        List<LibraryUser> result = getAllLibraryUserService.getAllLibraryUsers();

        assertNotNull(result);
        assertEquals(2, result.size());

        LibraryUser firstUser = result.getFirst();
        assertEquals(1L, firstUser.getId());
        assertEquals("John Doe", firstUser.getName());
        assertEquals("john@example.com", firstUser.getEmail());
        assertEquals("123456789", firstUser.getPhone());
        assertEquals(now, firstUser.getCreatedAt());

        LibraryUser secondUser = result.get(1);
        assertEquals(2L, secondUser.getId());
        assertEquals("Jane Doe", secondUser.getName());
        assertEquals("jane@example.com", secondUser.getEmail());
        assertEquals("987654321", secondUser.getPhone());
        assertEquals(now, secondUser.getCreatedAt());
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(libraryUserRepository.findAll())
                .thenReturn(List.of());

        List<LibraryUser> result = getAllLibraryUserService.getAllLibraryUsers();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}

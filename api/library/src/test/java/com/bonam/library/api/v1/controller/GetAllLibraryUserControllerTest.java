package com.bonam.library.api.v1.controller;

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
import org.springframework.http.HttpStatus;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.GetAllLibraryUserService;

@ExtendWith(MockitoExtension.class)
class GetAllLibraryUserControllerTest {

    @Mock
    private GetAllLibraryUserService getAllLibraryUserService;

    @InjectMocks
    private GetAllLibraryUserController controller;

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

        when(getAllLibraryUserService.getAllLibraryUsers())
                .thenReturn(users);

        var response = controller.getAllLibraryUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());

        var firstUser = responseBody.getFirst();
        assertEquals(1L, firstUser.getId());
        assertEquals("John Doe", firstUser.getName());
        assertEquals("john@example.com", firstUser.getEmail());
        assertEquals("123456789", firstUser.getPhone());
        assertEquals(now, firstUser.getCreatedAt());

        var secondUser = responseBody.get(1);
        assertEquals(2L, secondUser.getId());
        assertEquals("Jane Doe", secondUser.getName());
        assertEquals("jane@example.com", secondUser.getEmail());
        assertEquals("987654321", secondUser.getPhone());
        assertEquals(now, secondUser.getCreatedAt());
    }
}

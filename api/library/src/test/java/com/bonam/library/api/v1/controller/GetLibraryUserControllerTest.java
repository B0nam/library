package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;

@ExtendWith(MockitoExtension.class)
class GetLibraryUserControllerTest {

    @Mock
    private GetLibraryUserService getLibraryUserService;

    @InjectMocks
    private GetLibraryUserController controller;

    @Test
    void shouldGetLibraryUserSuccessfully() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

        LibraryUser user = LibraryUser.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        when(getLibraryUserService.getLibraryUserById(anyLong()))
                .thenReturn(user);

        var response = controller.getLibraryUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(userId, responseBody.getId());
        assertEquals("John Doe", responseBody.getName());
        assertEquals("john@example.com", responseBody.getEmail());
        assertEquals("123456789", responseBody.getPhone());
        assertEquals(now, responseBody.getCreatedAt());
    }
}

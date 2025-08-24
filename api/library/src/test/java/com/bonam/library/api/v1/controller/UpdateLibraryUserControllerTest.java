package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.api.v1.model.request.UpdateLibraryUserRequestDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.UpdateLibraryUserService;

@ExtendWith(MockitoExtension.class)
class UpdateLibraryUserControllerTest {

    @Mock
    private UpdateLibraryUserService updateLibraryUserService;

    @InjectMocks
    private UpdateLibraryUserController controller;

    @Test
    void shouldUpdateLibraryUserSuccessfully() {
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

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

        when(updateLibraryUserService.updateLibraryUser(anyLong(), any(UpdateLibraryUserRequestDTO.class)))
                .thenReturn(updatedUser);

        var response = controller.updateLibraryUser(userId, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(userId, responseBody.getId());
        assertEquals("John Doe Updated", responseBody.getName());
        assertEquals("john.updated@example.com", responseBody.getEmail());
        assertEquals("987654321", responseBody.getPhone());
        assertEquals(now, responseBody.getCreatedAt());
    }
}

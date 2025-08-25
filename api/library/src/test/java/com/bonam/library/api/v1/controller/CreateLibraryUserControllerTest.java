package com.bonam.library.api.v1.controller;

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
import org.springframework.http.HttpStatus;

import com.bonam.library.api.v1.model.request.CreateLibraryUserRequestDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.service.CreateLibraryUserService;

@ExtendWith(MockitoExtension.class)
class CreateLibraryUserControllerTest {

    @Mock
    private CreateLibraryUserService createLibraryUserService;

    @InjectMocks
    private CreateLibraryUserController controller;

    @Test
    void shouldCreateLibraryUserSuccessfully() {
        LocalDateTime now = LocalDateTime.now();
        CreateLibraryUserRequestDTO request = CreateLibraryUserRequestDTO.builder()
                .name("John Doe")
                .email("john@example.com")
                .telephone("123456789")
                .build();

        LibraryUser createdUser = LibraryUser.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .phone("123456789")
                .createdAt(now)
                .build();

        when(createLibraryUserService.createLibraryUser(any(LibraryUser.class)))
                .thenReturn(createdUser);

        var response = controller.createLibraryUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1L, responseBody.getId());
        assertEquals("John Doe", responseBody.getName());
        assertEquals("john@example.com", responseBody.getEmail());
        assertEquals("123456789", responseBody.getPhone());
        assertEquals(now, responseBody.getCreatedAt());
    }
}

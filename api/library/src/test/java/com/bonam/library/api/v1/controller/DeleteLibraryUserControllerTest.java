package com.bonam.library.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.bonam.library.domain.libraryusers.service.DeleteLibraryUserService;

@ExtendWith(MockitoExtension.class)
class DeleteLibraryUserControllerTest {

    @Mock
    private DeleteLibraryUserService removeLibraryUserService;

    @InjectMocks
    private DeleteLibraryUserController controller;

    @Test
    void shouldDeleteLibraryUserSuccessfully() {
        Long userId = 1L;
        doNothing().when(removeLibraryUserService).removeLibraryUserById(anyLong());

        var response = controller.removeLibraryUser(userId);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Library User removed successfully: " + userId, response.getBody());
        verify(removeLibraryUserService).removeLibraryUserById(userId);
    }
}

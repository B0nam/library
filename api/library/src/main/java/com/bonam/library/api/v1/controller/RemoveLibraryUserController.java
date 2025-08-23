package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.openapi.controller.RemoveLibraryUserControllerOpenApi;
import com.bonam.library.domain.libraryusers.service.RemoveLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library-user")
@RequiredArgsConstructor
public class RemoveLibraryUserController implements RemoveLibraryUserControllerOpenApi {

    private final RemoveLibraryUserService removeLibraryUserService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeLibraryUser(@PathVariable Long id) {
        removeLibraryUserService.removeLibraryUserById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Library User removed successfully: " + id);
    }
}

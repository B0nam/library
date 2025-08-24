package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.LibraryUserDTOMapper;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import com.bonam.library.api.v1.openapi.controller.GetLibraryUserControllerOpenApi;
import com.bonam.library.domain.libraryusers.service.GetLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library-user")
@RequiredArgsConstructor
public class GetLibraryUserController implements GetLibraryUserControllerOpenApi {

    private final GetLibraryUserService getLibraryUserService;

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUserResponseDTO> getLibraryUser(@PathVariable Long id) {
        var libraryUser = getLibraryUserService.getLibraryUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(LibraryUserDTOMapper.toResponseDTO(libraryUser));
    }
}

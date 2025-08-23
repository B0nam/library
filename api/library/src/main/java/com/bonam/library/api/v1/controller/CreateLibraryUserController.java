package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.LibraryUserDTOMapper;
import com.bonam.library.api.v1.model.request.CreateLibraryUserRequestDTO;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import com.bonam.library.api.v1.openapi.controller.CreateLibraryUserControllerOpenApi;
import com.bonam.library.domain.libraryusers.service.CreateLibraryUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library-user")
@RequiredArgsConstructor
public class CreateLibraryUserController implements CreateLibraryUserControllerOpenApi {

    private final CreateLibraryUserService createLibraryUserService;

    @PostMapping
    public ResponseEntity<LibraryUserResponseDTO> createLibraryUser(@Valid @RequestBody CreateLibraryUserRequestDTO request) {
        var libraryUser = LibraryUserDTOMapper.toEntity(request);
        var createdLibraryUser = createLibraryUserService.createLibraryUser(libraryUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(LibraryUserDTOMapper.toResponseDTO(createdLibraryUser));
    }
}

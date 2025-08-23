package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.model.request.UpdateLibraryUserRequestDTO;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import com.bonam.library.api.v1.mapper.LibraryUserDTOMapper;
import com.bonam.library.api.v1.openapi.controller.UpdateLibraryUserControllerOpenApi;
import com.bonam.library.api.v1.service.UpdateLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library-user")
@RequiredArgsConstructor
public class UpdateLibraryUserController implements UpdateLibraryUserControllerOpenApi {

    private final UpdateLibraryUserService updateLibraryUserService;

    @PutMapping("/{id}")
    public ResponseEntity<LibraryUserResponseDTO> updateLibraryUser(
            @PathVariable Long id,
            @RequestBody UpdateLibraryUserRequestDTO request) {
        var updatedLibraryUser = updateLibraryUserService.updateLibraryUser(id, request);
        return ResponseEntity.ok(LibraryUserDTOMapper.toResponseDTO(updatedLibraryUser));
    }
}

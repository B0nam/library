package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.LibraryUserDTOMapper;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import com.bonam.library.api.v1.openapi.controller.GetAllLibraryUserControllerOpenApi;
import com.bonam.library.domain.libraryusers.service.GetAllLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library-users")
@RequiredArgsConstructor
public class GetAllLibraryUserController implements GetAllLibraryUserControllerOpenApi {

    private final GetAllLibraryUserService getAllLibraryUserService;

    @GetMapping
    public ResponseEntity<List<LibraryUserResponseDTO>> getAllLibraryUsers() {
        var libraryUsers = getAllLibraryUserService.getAllLibraryUsers();
        return ResponseEntity.status(HttpStatus.OK).body(LibraryUserDTOMapper.toResponseDTOList(libraryUsers));
    }
}

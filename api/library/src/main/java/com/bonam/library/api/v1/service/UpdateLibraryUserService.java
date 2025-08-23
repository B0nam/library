package com.bonam.library.api.v1.service;

import com.bonam.library.api.v1.model.request.UpdateLibraryUserRequestDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Transactional
    public LibraryUser updateLibraryUser(Long id, UpdateLibraryUserRequestDTO request) {
        var libraryUser = libraryUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Library user not found with id: " + id));

        var updatedUser = LibraryUser.builder()
                .id(libraryUser.getId())
                .name(Optional.ofNullable(request.getName()).orElse(libraryUser.getName()))
                .email(Optional.ofNullable(request.getEmail()).orElse(libraryUser.getEmail()))
                .phone(Optional.ofNullable(request.getPhone()).orElse(libraryUser.getPhone()))
                .createdAt(libraryUser.getCreatedAt())
                .build();

        return libraryUserRepository.save(updatedUser);
    }
}

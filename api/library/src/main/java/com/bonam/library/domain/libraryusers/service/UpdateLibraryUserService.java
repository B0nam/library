package com.bonam.library.domain.libraryusers.service;

import com.bonam.library.api.v1.model.request.UpdateLibraryUserRequestDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateLibraryUserService {

    private final GetLibraryUserService getLibraryUserService;
    private final LibraryUserRepository libraryUserRepository;

    @Transactional
    public LibraryUser updateLibraryUser(Long id, UpdateLibraryUserRequestDTO request) {
        var libraryUser = getLibraryUserService.getLibraryUserById(id);

        libraryUser.setName(Optional.ofNullable(request.getName()).orElse(libraryUser.getName()));
        libraryUser.setEmail(Optional.ofNullable(request.getEmail()).orElse(libraryUser.getEmail()));
        libraryUser.setPhone(Optional.ofNullable(request.getPhone()).orElse(libraryUser.getPhone()));

        return libraryUserRepository.save(libraryUser);
    }
}

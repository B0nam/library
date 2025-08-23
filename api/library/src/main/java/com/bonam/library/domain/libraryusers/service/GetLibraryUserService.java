package com.bonam.library.domain.libraryusers.service;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Transactional(readOnly = true)
    public LibraryUser getLibraryUserById(Long id) {
        return libraryUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library user not found with id: " + id)); // TODO: EXCEPTION
    }
}

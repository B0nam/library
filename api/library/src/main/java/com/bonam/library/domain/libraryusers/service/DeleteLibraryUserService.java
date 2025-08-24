package com.bonam.library.domain.libraryusers.service;

import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;
    private final GetLibraryUserService getLibraryUserService;

    @Transactional
    public void removeLibraryUserById(Long id) {
        var libraryUser = getLibraryUserService.getLibraryUserById(id);
        if(libraryUser == null) {
            throw new RuntimeException("Library user not found with id: " + id); // TODO: EXCEPTION
        }
        libraryUserRepository.deleteById(id);
    }
}

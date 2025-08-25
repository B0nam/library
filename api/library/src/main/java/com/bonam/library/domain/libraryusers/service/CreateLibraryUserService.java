package com.bonam.library.domain.libraryusers.service;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Transactional
    public LibraryUser createLibraryUser(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }
}

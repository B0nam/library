package com.bonam.library.domain.libraryusers.service;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import com.bonam.library.domain.libraryusers.repository.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    public List<LibraryUser> getAllLibraryUsers() {
        return libraryUserRepository.findAll();
    }
}

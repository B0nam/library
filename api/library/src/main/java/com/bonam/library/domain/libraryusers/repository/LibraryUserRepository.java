package com.bonam.library.domain.libraryusers.repository;

import com.bonam.library.domain.libraryusers.model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
}

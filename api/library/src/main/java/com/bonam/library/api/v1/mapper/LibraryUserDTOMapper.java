package com.bonam.library.api.v1.mapper;

import com.bonam.library.api.v1.model.request.CreateLibraryUserRequestDTO;
import com.bonam.library.api.v1.model.response.LibraryUserResponseDTO;
import com.bonam.library.domain.libraryusers.model.LibraryUser;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class LibraryUserDTOMapper {

    public static LibraryUser toEntity(CreateLibraryUserRequestDTO request) {
        return LibraryUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getTelephone())
                .build();
    }

    public static LibraryUserResponseDTO toResponseDTO(LibraryUser libraryUser) {
        return LibraryUserResponseDTO.builder()
                .id(libraryUser.getId())
                .name(libraryUser.getName())
                .email(libraryUser.getEmail())
                .phone(libraryUser.getPhone())
                .createdAt(libraryUser.getCreatedAt())
                .build();
    }

    public static List<LibraryUserResponseDTO> toResponseDTOList(List<LibraryUser> libraryUsers) {
        return libraryUsers.stream()
                .map(LibraryUserDTOMapper::toResponseDTO)
                .toList();
    }
}

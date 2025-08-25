package com.bonam.library.api.v1.model.response;

import com.bonam.library.api.v1.openapi.model.LibraryUserResponseDTOOpenApi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LibraryUserResponseDTO implements LibraryUserResponseDTOOpenApi {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private LocalDateTime createdAt;
}

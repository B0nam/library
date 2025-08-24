package com.bonam.library.api.v1.model.response;

import com.bonam.library.api.v1.openapi.model.LibraryUserResponseDTOOpenApi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LibraryUserResponseDTO implements LibraryUserResponseDTOOpenApi {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private LocalDateTime createdAt;
}

package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.CreateLibraryUserRequestDTOOpenApi;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateLibraryUserRequestDTO implements CreateLibraryUserRequestDTOOpenApi {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Telephone is required")
    private String telephone;
}

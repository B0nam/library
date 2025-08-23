package com.bonam.library.api.v1.model.request;

import com.bonam.library.api.v1.openapi.model.UpdateLibraryUserRequestDTOOpenApi;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateLibraryUserRequestDTO implements UpdateLibraryUserRequestDTOOpenApi {

    private String name;

    private String email;

    private String phone;
}

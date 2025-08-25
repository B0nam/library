package com.bonam.library.domain.googlebooks.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleBooksResponseDTO {
    private String kind;
    private Integer totalItems;
    private List<GoogleBookItemDTO> items;
}

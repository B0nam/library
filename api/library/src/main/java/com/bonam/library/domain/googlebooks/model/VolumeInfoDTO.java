package com.bonam.library.domain.googlebooks.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VolumeInfoDTO {
    private String title;
    private List<String> authors;
    private String publishedDate;
    private List<IndustryIdentifierDTO> industryIdentifiers;
    private List<String> categories;
}

package com.bonam.library.domain.googlebooks.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleBookItemDTO {
    private String id;
    private VolumeInfoDTO volumeInfo;
}

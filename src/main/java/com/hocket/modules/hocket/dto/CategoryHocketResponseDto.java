package com.hocket.modules.hocket.dto;

import com.hocket.modules.hocket.Hocket;
import lombok.Data;

@Data
public class CategoryHocketResponseDto {

    private String title;

    private String thumbnailImage;

    private int numberOfHearts;

    public CategoryHocketResponseDto(Hocket hocket) {
        this.title = hocket.getTitle();
        this.thumbnailImage = hocket.getThumbnailImage();
        this.numberOfHearts = hocket.getLikeHearts().size();
    }
}

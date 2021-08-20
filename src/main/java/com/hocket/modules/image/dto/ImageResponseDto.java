package com.hocket.modules.image.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageResponseDto {
    private String url;

    private LocalDateTime addDateTime;
}

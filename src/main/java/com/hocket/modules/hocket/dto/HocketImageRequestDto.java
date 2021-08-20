package com.hocket.modules.hocket.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HocketImageRequestDto {

    @NotBlank
    private String token;

    @NotBlank
    private String hocketId;
}

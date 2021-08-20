package com.hocket.modules.hocket.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class AddImageForm {

    @NotBlank
    private String token;

    @NotBlank
    private String hocketId;

    @NotBlank
    private MultipartFile image;
}

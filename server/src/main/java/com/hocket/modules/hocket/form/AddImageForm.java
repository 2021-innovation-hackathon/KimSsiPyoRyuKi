package com.hocket.modules.hocket.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class AddImageForm {

    private String token;

    private String hocketId;

    private MultipartFile image;
}

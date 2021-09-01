package com.hocket.modules.likeheart.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddWishHocketForm {

    @NotBlank
    private String token;

    @NotBlank
    private Long hocketId;
}

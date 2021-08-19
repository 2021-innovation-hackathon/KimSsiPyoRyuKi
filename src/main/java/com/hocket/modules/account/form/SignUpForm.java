package com.hocket.modules.account.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpForm {

    @NotBlank
    private String token;
    @NotBlank
    private String nickname;

}

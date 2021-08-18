package com.hocket.modules.account.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpForm {

    @NotBlank
    private String email;

    @NotBlank
    private String token;

    @NotBlank
    private String nickname;

    private String name;

    private String gender;

    private int age;

    private String profileImage;
}

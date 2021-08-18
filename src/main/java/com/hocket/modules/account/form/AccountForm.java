package com.hocket.modules.account.form;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AccountForm {

    @NotBlank
    private String email;

    @NotBlank
    private String token;

    private String name;

    private String nickname;

    private String gender;

    private int age;

    private String profileImage;


}

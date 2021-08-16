package com.hocket.modules.account.form;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AccountForm {

    private String nickname;

    @NotBlank
    private String name;

    private String email;

    @NotBlank
    private String token;

}

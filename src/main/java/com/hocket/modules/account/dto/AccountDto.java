package com.hocket.modules.account.dto;

import lombok.Data;


@Data
public class AccountDto {

    private Long id;

    private String nickname;

    private String email;

    private String name;

    private String gender;

    private Integer age;

    private String profileImage;

}

package com.hocket.modules.account.dto;

import com.hocket.modules.likeheart.LikeHeart;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class AccountDto {

    private Long id;

    private String nickname;

    private String email;

    private String name;

    private String gender;

    private Integer age;

    private String profileImage;

    private Set<LikeHeart> likeHearts = new HashSet<>();
}

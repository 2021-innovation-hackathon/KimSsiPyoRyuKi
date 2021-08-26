package com.hocket.modules.kakao.dto;

import com.hocket.modules.account.Account;
import lombok.Data;

@Data
public class KakaoUserInfoResponseDto {

    private String nickname;
    private String email;
    private String age_range;
    private String gender;

    public Account toEntity(){
        Account account = new Account();
        account.setNickname(this.nickname);
        account.setEmail(this.email);
        account.setGender(this.gender);
        account.setAgeRange(this.age_range);

        return  account;
    }

}

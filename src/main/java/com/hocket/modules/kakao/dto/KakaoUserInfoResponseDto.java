package com.hocket.modules.kakao.dto;

import com.hocket.modules.account.Account;
import lombok.Data;

@Data
public class KakaoUserInfoResponseDto {

    private String kakao_accountProfileNickname;
    private String kakao_accountEmail;
    private String kakao_accountAge_range;
    private String kakao_accountGender;

    public Account toEntity(){
        Account account = new Account();
        account.setNickname(this.kakao_accountProfileNickname);
        account.setEmail(this.kakao_accountEmail);
        account.setGender(this.kakao_accountAge_range);
        account.setAgeRange(this.kakao_accountGender);

        return  account;
    }

}

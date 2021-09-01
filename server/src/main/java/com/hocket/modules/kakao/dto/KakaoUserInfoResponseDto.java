package com.hocket.modules.kakao.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@NoArgsConstructor
@Data
public class KakaoUserInfoResponseDto {

    private String nickname;
    private String email;
    private String ageRange;
    private String gender;

    public Account toEntity(){
        Account account = new Account();
        account.setNickname(this.nickname);
        account.setEmail(this.email);
        account.setGender(this.ageRange);
        account.setAgeRange(this.gender);

        return  account;
    }

    public KakaoUserInfoResponseDto(ResponseEntity<JsonNode> responseEntity){

        JsonNode body = responseEntity.getBody();

        this.nickname = body.findValue("nickname").textValue();
        this.email = body.findValue("email").textValue();
        this.ageRange = body.findValue("age_range").textValue();
        this.gender = body.findValue("gender").textValue();
    }


}

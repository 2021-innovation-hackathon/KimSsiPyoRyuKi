package com.hocket.modules.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.account.form.LoginForm;
import com.hocket.modules.account.form.SignUpForm;
import com.sun.net.httpserver.Headers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    private final CacheManager cacheManager;

    private RestTemplate restTemplate = new RestTemplate();


    public Account saveAccount(JsonNode userInfo, String nickname) {
        Account account = new Account();

        account.setEmail(userInfo.findValue("email").textValue());
        account.setName(Objects.isNull(userInfo.findValue("nickname"))?null: userInfo.findValue("nickname").textValue());
        account.setGender(Objects.isNull(userInfo.findValue("gender"))?null: userInfo.findValue("gender").textValue());
        account.setAgeRange(Objects.isNull(userInfo.findValue("age_range"))?null: userInfo.findValue("age_range").textValue());
        account.setProfileImage(Objects.isNull(userInfo.findValue("thumbnail_image_url"))?null: userInfo.findValue("thumbnail_image_url").textValue());
        account.setNickname(nickname);

        Account newAccount = accountRepository.save(account);

        return newAccount;
    }

    @CachePut(cacheNames = "account", key = "#token")
    public Long login(Long accountId, String token){
        //save Cache <Token, account Id>

        return  accountId;
    }

    @CacheEvict(cacheNames = "account", key = "#token")
    public void logout(String token){

        //remove Cache findBy token
    }

    public boolean checkToken(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization","Bearer "+token);

        HttpEntity entity = new HttpEntity(headers);
        try{
            ResponseEntity<Map> responseEntity =  restTemplate.exchange("https://kapi.kakao.com/v1/user/access_token_info", HttpMethod.GET,entity, Map.class);
            if(!responseEntity.getStatusCode().is2xxSuccessful()){
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }

    public JsonNode getInfoByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + token);

        HttpEntity entity = new HttpEntity(headers);

        try {
            ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity("https://kapi.kakao.com/v2/user/me", entity, JsonNode.class);
            System.out.println("getInfoToken Response: " + responseEntity);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            }

        }catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}

package com.hocket.modules.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Slf4j
@Repository
public class KakaoRepository {

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private final String CHECKTOKEN_URL = "https://kapi.kakao.com/v1/user/access_token_info";
    private final String GET_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private RestTemplate restTemplate = new RestTemplate();

    public boolean validation(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,CONTENT_TYPE);
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+token);

        HttpEntity<Map<String, String>> entity = new HttpEntity(headers);
        ResponseEntity<Map<String, String>> responseEntity =  restTemplate.exchange(CHECKTOKEN_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return true;
        }
        log.info("check token failed");


        throw new IllegalArgumentException("유효하지 않은 토큰 입니다.");

    }

    public KakaoUserInfoResponseDto getInfoByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<KakaoUserInfoResponseDto> responseEntity = restTemplate.postForEntity(GET_USER_INFO_URL, entity, KakaoUserInfoResponseDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        log.info("get info failed");

        throw new IllegalArgumentException("유저 정보를 불러오는데 실패했습니다.");
    }
}

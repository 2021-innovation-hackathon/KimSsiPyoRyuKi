package com.hocket.modules.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;


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
        HttpEntity entity = makeHttpEntity(token);
        ResponseEntity<Map<String, String>> validationResponse = getValidationResponseFromKakao(entity);
        return is2xxElseThrow(token, validationResponse);
    }

    public KakaoUserInfoResponseDto getInfoByToken(String token) {
        HttpEntity entity = makeHttpEntity(token);
        ResponseEntity<JsonNode> responseEntity = getUserInfoFromKakao(entity);
        return getUserInfoDtoThrowOnFailure(responseEntity);
    }

    private KakaoUserInfoResponseDto getUserInfoDtoThrowOnFailure(ResponseEntity<JsonNode> responseEntity) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            KakaoUserInfoResponseDto infoResponseDto = new KakaoUserInfoResponseDto(responseEntity);

            return infoResponseDto;
        }
        log.info("Failed to Get User Information");
        throw new IllegalArgumentException("유저 정보를 불러오는데 실패했습니다.");
    }

    private boolean is2xxElseThrow(String token, ResponseEntity<Map<String, String>> validationResponse) {
        if(validationResponse.getStatusCode().is2xxSuccessful()){
            return true;
        }
        log.info("Invalid Token: ", token);
        throw new IllegalArgumentException("유효하지 않은 토큰 입니다.");
    }

    private ResponseEntity<Map<String, String>> getValidationResponseFromKakao(HttpEntity entity) {
        return restTemplate.exchange(CHECKTOKEN_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }

    private ResponseEntity<JsonNode> getUserInfoFromKakao(HttpEntity entity) {
        return restTemplate.exchange(GET_USER_INFO_URL, HttpMethod.POST, entity, JsonNode.class);
    }

    private HttpEntity<Map<String, String>> makeHttpEntity(String token) {
        HttpHeaders headers = makeHttpHeader(token);
        return new HttpEntity(headers);
    }

    private HttpHeaders makeHttpHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}

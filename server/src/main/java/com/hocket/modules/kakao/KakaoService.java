package com.hocket.modules.kakao;

import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final KakaoRepository kakaoRepository;

    public boolean checkToken(String token){
        return kakaoRepository.validation(token);
    }

    public KakaoUserInfoResponseDto getInfoByToken(String token){
        return kakaoRepository.getInfoByToken(token);
    }
}

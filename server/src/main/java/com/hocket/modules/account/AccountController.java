package com.hocket.modules.account;


import com.hocket.modules.kakao.KakaoService;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final KakaoService kakaoService;


    @PostMapping("/sign-up")
    public ResponseEntity signUp(String token) {
        accountService.createAccountAndLogin(token);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/account/check")
    public ResponseEntity isExistsAccount(String token){
        KakaoUserInfoResponseDto userInfo = kakaoService.getInfoByToken(token);
        if(!accountRepository.existsByEmail(userInfo.getEmail())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}

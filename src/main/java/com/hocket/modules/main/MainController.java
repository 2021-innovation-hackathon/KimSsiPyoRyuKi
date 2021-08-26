package com.hocket.modules.main;

import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.kakao.KakaoService;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MainController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final KakaoService kakaoService;



    @PostMapping("/login")
    public ResponseEntity login(String token){

        kakaoService.checkToken(token);

        KakaoUserInfoResponseDto userInfo = kakaoService.getInfoByToken(token);
        if(userInfo.getEmail() == null){
            return ResponseEntity.badRequest().build();
        }
        String email = userInfo.getEmail();

        Account account = accountRepository.findByEmail(email);

        if(account == null){
            return ResponseEntity.notFound().build();
        }
        else{
            accountService.login(account.getId(), token);
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity logout(String token){
        accountService.logout(token);

        return ResponseEntity.ok().build();
    }



}

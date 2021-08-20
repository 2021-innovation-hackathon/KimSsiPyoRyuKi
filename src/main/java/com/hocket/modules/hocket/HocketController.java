package com.hocket.modules.hocket;

import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.dto.SimpleHocketResponseDto;
import com.hocket.modules.hocket.form.HocketForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HocketController {
    private  final HocketService hocketService;
    private final AccountService accountService;


    @PostMapping("/hocket/create")
    public ResponseEntity createHocket(@Valid HocketForm hocketForm){

        Long accountId = accountService.getAccountIdByToken(hocketForm.getToken());

        //이메일 동의가 안됐거나, 토큰이 올바르지 않거나, 회원가입이 되지 않음.
        if(accountId == null){
            return ResponseEntity.badRequest().build();
        }

        hocketService.createHocket(hocketForm, accountId);


        return ResponseEntity.ok().build();
    }

    @PostMapping("/hocket/simpleList")
    public List<SimpleHocketResponseDto> getSimpleHocketList(String token){

        Long accountId = accountService.getAccountIdByToken(token);
        List<SimpleHocketResponseDto> simpleinfo = hocketService.getSimpleinfo(accountId);

        return simpleinfo;
    }



//    @PostMapping("/hocket/test")
//    public String S3test(@RequestParam("image") MultipartFile multipartFile){
//
//        String thumbnail = uploadS3.uploadImageToS3(multipartFile, "thumbnail", "1");
//        System.out.println(thumbnail);
//
//        return thumbnail;
//    }

}

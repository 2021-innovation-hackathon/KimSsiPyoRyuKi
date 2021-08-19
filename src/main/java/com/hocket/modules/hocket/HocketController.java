package com.hocket.modules.hocket;

import com.hocket.infra.s3.UploadS3;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.form.HocketForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class HocketController {
    private HocketService hocketService;
    private final AccountService accountService;

    private final UploadS3 uploadS3;


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

//    @PostMapping("/hocket/test")
//    public String test(@RequestParam("image") MultipartFile multipartFile){
//
//        String thumbnail = uploadS3.uploadImageToS3(multipartFile, "thumbnail", "1");
//        System.out.println(thumbnail);
//
//        return thumbnail;
//    }

}

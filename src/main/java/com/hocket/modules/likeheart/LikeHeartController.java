package com.hocket.modules.likeheart;

import com.hocket.modules.account.AccountService;
import com.hocket.modules.likeheart.form.AddWishHocketForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class LikeHeartController {

    private final LikeHeartService likeHeartService;
    private final AccountService accountService;


    @PostMapping("/likeHeart/add")
    public ResponseEntity addWishHocket(@Valid AddWishHocketForm addWishHocketForm){

        Long accountId = accountService.getAccountIdByToken(addWishHocketForm.getToken());

        if(accountId == null){
            return ResponseEntity.badRequest().build();
        }
        likeHeartService.addWishHocket(accountId, addWishHocketForm.getHocketId());

        return ResponseEntity.ok().build();
    }

}

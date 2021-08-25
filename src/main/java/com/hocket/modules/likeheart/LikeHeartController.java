package com.hocket.modules.likeheart;

import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.Hocket;
import com.hocket.modules.hocket.HocketRepository;
import com.hocket.modules.likeheart.form.AddWishHocketForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LikeHeartController {

    private final LikeHeartService likeHeartService;
    private final AccountService accountService;
    private final HocketRepository hocketRepository;


    @PostMapping("/likeHeart/add")
    public ResponseEntity addWishHocket(@Valid AddWishHocketForm addWishHocketForm){

        Long accountId = accountService.getAccountIdByToken(addWishHocketForm.getToken());

        if(accountId == null){
            return ResponseEntity.badRequest().build();
        }
        Optional<Hocket> byId = hocketRepository.findById(addWishHocketForm.getHocketId());
        if(byId.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        likeHeartService.addWishHocket(accountId, byId.get());

        return ResponseEntity.ok().build();
    }

}

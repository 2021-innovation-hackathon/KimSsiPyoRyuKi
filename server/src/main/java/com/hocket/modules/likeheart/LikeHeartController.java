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

    @PostMapping("/likeHeart")
    public ResponseEntity addWishHocket(@Valid AddWishHocketForm addWishHocketForm){
        likeHeartService.addWishHocket(addWishHocketForm);
        return ResponseEntity.ok().build();
    }

}

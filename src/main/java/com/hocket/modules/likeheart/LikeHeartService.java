package com.hocket.modules.likeheart;

import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.hocket.Hocket;
import com.hocket.modules.hocket.HocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LikeHeartService {

    private final LikeHeartRepository likeHeartRepository;
    private final HocketRepository hocketRepository;
    private final AccountRepository accountRepository;

    public void addWishHocket(Long accountId, Hocket hocket) {

        LikeHeart likeHeart = new LikeHeart();
        likeHeart.setHocket(hocket);
        likeHeart.setAccount(accountRepository.findById(accountId).get());

        likeHeartRepository.save(likeHeart);
    }
}

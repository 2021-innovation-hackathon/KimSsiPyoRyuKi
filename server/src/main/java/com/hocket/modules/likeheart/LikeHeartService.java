package com.hocket.modules.likeheart;


import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.Hocket;
import com.hocket.modules.hocket.HocketRepository;
import com.hocket.modules.likeheart.form.AddWishHocketForm;

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
    private final AccountService accountService;

    public void addWishHocket(AddWishHocketForm addWishHocketForm) {
        Account account = getAccountByToken(addWishHocketForm.getToken());
        Hocket hocket = getHocketById(addWishHocketForm.getHocketId());

        addLikeHeart(account, hocket);
    }

    private void addLikeHeart(Account account, Hocket hocket) {
        LikeHeart likeHeart = new LikeHeart(account, hocket);
        likeHeartRepository.save(likeHeart);
    }

    private Hocket getHocketById(Long hocketId) {
        return hocketRepository.findById(hocketId).orElseThrow();
    }

    private Account getAccountByToken(String token) {
        Long accountId = accountService.getAccountIdByToken(token);
        return accountRepository.findById(accountId).orElseThrow();
    }
}

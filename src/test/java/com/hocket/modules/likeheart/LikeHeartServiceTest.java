package com.hocket.modules.likeheart;

import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.hocket.Hocket;
import com.hocket.modules.hocket.HocketFactory;
import com.hocket.modules.hocket.HocketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class LikeHeartServiceTest {

    @Autowired
    LikeHeartService likeHeartService;
    @Autowired
    AccountFactory accountFactory;
    @Autowired
    HocketFactory hocketFactory;

    @Autowired
    LikeHeartRepository likeHeartRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    HocketRepository hocketRepository;

    @BeforeEach
    void cleaup(){
        likeHeartRepository.deleteAll();
        hocketRepository.deleteAll();
        accountRepository.deleteAll();
    }


    @DisplayName("하켓 위시 리스트에 추가")
    @Test
    void addWishHocket() throws Exception {

        String tokent = UUID.randomUUID().toString();

        Account account = accountFactory.createNewAccount("김태준", "test@email.com");
        Hocket hocket = hocketFactory.createNewHocket(account, tokent);

        likeHeartService.addWishHocket(account.getId(),hocket.getId());

        LikeHeart likeHeart = likeHeartRepository.findAll().get(0);

        assertNotNull(likeHeart);
        assertThat(likeHeart.getHocket()).isEqualTo(hocket);
        assertThat(likeHeart.getAccount()).isEqualTo(account);


    }



}
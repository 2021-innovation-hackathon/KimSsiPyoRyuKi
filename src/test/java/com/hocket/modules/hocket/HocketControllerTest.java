package com.hocket.modules.hocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.likeheart.LikeHeart;
import com.hocket.modules.likeheart.LikeHeartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.opaqueToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HocketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    HocketRepository hocketRepository;

    @Autowired
    AccountFactory accountFactory;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    HocketService hocketService;
    @Autowired
    LikeHeartRepository likeHeartRepository;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AccountService accountService;


    @BeforeEach
    void cleanUp(){
        hocketRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @DisplayName("Simple Hocket List 테스트")
    @Test
    void getSimpleList() throws Exception {

        String token = UUID.randomUUID().toString();

        Account account = accountFactory.createNewAccount("김태준", "test@email.com");
        when(accountService.getAccountIdByToken(token)).thenReturn(account.getId());

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusMinutes(20);

        HocketForm hocketForm = new HocketForm();
        hocketForm.setToken(token);
        hocketForm.setTitle("title");
        hocketForm.setPublic(true);
        hocketForm.setAchieved(false);
        hocketForm.setRequireDate(true);
        hocketForm.setStartDateTime(start);
        hocketForm.setEndDateTime(end);

        hocketService.createHocket(hocketForm, account.getId());

        Hocket hocket = hocketRepository.findByAccountId(account.getId()).get(0);

        LikeHeart likeHeart = new LikeHeart();
        likeHeart.setHocket(hocket);
        likeHeart.setAccount(account);

        likeHeartRepository.save(likeHeart);


        MvcResult mvcResult = mockMvc.perform(post("/hocket/simpleList")
                .param("token", token)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode =  objectMapper.readTree(mvcResult.getResponse().getContentAsString());

        assertThat(jsonNode.findValue("numberOfHearts").asInt()).isEqualTo(1);
        assertThat(jsonNode.findValue("title").textValue()).isEqualTo("title");
        assertThat(jsonNode.findValue("startDateTime").textValue()).isEqualTo(start.toString());
        assertThat(jsonNode.findValue("endDateTime").textValue()).isEqualTo(end.toString());
        assertThat(jsonNode.findValue("id").asLong()).isEqualTo(hocket.getId());



    }


}
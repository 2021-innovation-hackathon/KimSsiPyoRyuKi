package com.hocket.modules.hocket;

import com.hocket.infra.s3.UploadS3;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.form.HocketForm;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class HocketServiceTest {

    @Autowired
    HocketService hocketService;

    @Autowired
    HocketRepository hocketRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountFactory accountFactory;

    @Autowired
    AccountService accountService;

    @MockBean
    UploadS3 uploadS3;


    @BeforeEach
    void cleanUp(){
        hocketRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @DisplayName("하켓 생성 테스트")
    @Test
    void createHocket() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("test.png",new FileInputStream("src/test/resources/static/img/testImage.jpeg"));

        String token = UUID.randomUUID().toString();

        Account account = accountFactory.createNewAccount("bigave", "test@eamil.com");
        accountService.login(account.getId(), token);

        when(uploadS3.uploadImageToS3(multipartFile,"thumbnail", String.valueOf(account.getId()))).thenReturn("url");

        HocketForm hocketForm = new HocketForm();
        hocketForm.setAchieved(false);
        hocketForm.setPublic(true);
        hocketForm.setTitle("하켓 생성 테스트");
        hocketForm.setToken(token);
        hocketForm.setRequireDate(false);
        hocketForm.setThumbnailImage(multipartFile);

        hocketService.createHocket(hocketForm, account.getId());
        Hocket hocket = hocketRepository.findAll().get(0);
        assertNotNull(hocket);
    }

}
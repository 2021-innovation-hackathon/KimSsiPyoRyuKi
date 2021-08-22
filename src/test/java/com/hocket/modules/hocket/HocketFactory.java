package com.hocket.modules.hocket;

import com.hocket.modules.account.Account;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.likeheart.LikeHeart;
import com.hocket.modules.likeheart.LikeHeartRepository;
import org.junit.jupiter.params.provider.EnumSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;


@Component
public class HocketFactory {

    @Autowired
    HocketRepository hocketRepository;

    @Autowired
    HocketService hocketService;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    LikeHeartRepository likeHeartRepository;

    public Hocket createNewHocket(Account account, String token){

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
        hocketForm.setCategoryTitles(new HashSet<>(){
            {
                add("home");
                add("etc");
            }
        });

        Hocket hocket = hocketService.createHocket(hocketForm, account.getId());

        LikeHeart likeHeart = new LikeHeart();
        likeHeart.setHocket(hocket);
        likeHeart.setAccount(account);
        likeHeartRepository.save(likeHeart);

        return hocket;

    }
}

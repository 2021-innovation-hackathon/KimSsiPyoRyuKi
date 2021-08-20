package com.hocket.modules.hocket;

import com.hocket.modules.account.Account;
import com.hocket.modules.hocket.form.HocketForm;
import org.junit.jupiter.params.provider.EnumSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;


@Component
public class HocketFactory {

    @Autowired
    HocketRepository hocketRepository;

    @Autowired
    ModelMapper modelMapper;

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

        Hocket hocket = modelMapper.map(hocketForm, Hocket.class);
        hocket.setAccount(account);

        Hocket newHocket = hocketRepository.save(hocket);

        return newHocket;

    }
}

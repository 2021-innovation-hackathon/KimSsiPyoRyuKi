package com.hocket.modules.hocket;

import com.hocket.infra.s3.UploadS3;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.form.HocketForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class HocketService {

    private final HocketRepository hocketRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UploadS3 uploadS3;



    public void createHocket(HocketForm hocketForm, Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        Hocket hocket = modelMapper.map(hocketForm, Hocket.class);
        hocket.setAccount(account);

        Hocket newHocket = hocketRepository.save(hocket);

        String S3Path = uploadS3.uploadImageToS3(hocketForm.getThumbnailImage(), "thumbnail", String.valueOf(newHocket.getId()));

        newHocket.setThumbnailImage(S3Path);
    }
}

package com.hocket.modules.hocket;

import com.hocket.infra.s3.UploadS3;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.category.Category;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class HocketService {

    private final HocketRepository hocketRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final UploadS3 uploadS3;
    private final CategoryRepository categoryRepository;



    public void createHocket(HocketForm hocketForm, Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        Hocket hocket = modelMapper.map(hocketForm, Hocket.class);
        hocket.setAccount(account);

        Hocket newHocket = hocketRepository.save(hocket);

        List<Category> collect = hocketForm.getCategoryTitles().stream()
                .map(t -> categoryRepository.findByTitle(t))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for(Category c : collect){
            if(c != null){
                newHocket.getCategories().add(c);
            }
        }

        String S3Path = uploadS3.uploadImageToS3(hocketForm.getThumbnailImage(), "thumbnail", String.valueOf(newHocket.getId()));

        newHocket.setThumbnailImage(S3Path);
    }
}

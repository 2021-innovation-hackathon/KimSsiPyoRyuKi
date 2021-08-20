package com.hocket.modules.hocket;

import com.hocket.infra.s3.UploadS3;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.category.Category;
import com.hocket.modules.hocket.dto.SimpleHocketResponseDto;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.category.CategoryRepository;
import com.hocket.modules.image.Image;
import com.hocket.modules.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private final ImageRepository imageRepository;




    public void createHocket(HocketForm hocketForm, Long accountId) {
        Account account = accountRepository.findById(accountId).get();

        Hocket hocket = modelMapper.map(hocketForm, Hocket.class);
        hocket.setAccount(account);

        Hocket newHocket = hocketRepository.save(hocket);

        if(hocketForm.getCategoryTitles() != null){

            List<Category> collect = hocketForm.getCategoryTitles().stream()
                    .map(t -> categoryRepository.findByTitle(t))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for(Category c : collect){
                if(c != null){
                    newHocket.getCategories().add(c);
                }
            }
        }


        if( hocketForm.getThumbnailImage() != null){
            String S3Path = uploadS3.uploadImageToS3(hocketForm.getThumbnailImage(), "thumbnail", String.valueOf(newHocket.getId()));
            newHocket.setThumbnailImage(S3Path);
        }

    }

    public List<SimpleHocketResponseDto> getSimpleinfo(Long accountId) {
        List<Hocket> hockets = hocketRepository.findByAccountId(accountId);
        if(hockets == null){
            return null;
        }

        return hockets.stream()
                    .map(h -> {
                        SimpleHocketResponseDto responseDto = modelMapper.map(h, SimpleHocketResponseDto.class);
                        responseDto.setNumberOfHearts(h.getLikeHearts().size());
                        return responseDto;
                    })
                    .collect(Collectors.toList());
    }

    public void addImage(Long hocketId, MultipartFile multipartFile) {
        String url = uploadS3.uploadImageToS3(multipartFile, "hocketImage", String.valueOf(hocketId));

        Image image = new Image();
        image.setUrl(url);
        image.setHocket(hocketRepository.findById(hocketId).get());
        image.setAddDateTime(LocalDateTime.now());

        imageRepository.save(image);

    }


}

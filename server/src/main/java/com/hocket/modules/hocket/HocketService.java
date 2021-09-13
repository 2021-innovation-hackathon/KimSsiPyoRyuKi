package com.hocket.modules.hocket;

import com.hocket.exception.BadRequestException;
import com.hocket.infra.s3.UploadS3;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.category.Category;
import com.hocket.modules.hocket.dto.CategoryHocketResponseDto;
import com.hocket.modules.hocket.dto.HocketResponseDto;
import com.hocket.modules.hocket.dto.SimpleHocketResponseDto;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.category.CategoryRepository;
import com.hocket.modules.image.Image;
import com.hocket.modules.image.ImageRepository;
import com.hocket.modules.image.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private final ImageRepository imageRepository;




    public Hocket createHocket(HocketForm hocketForm, Long accountId) {
        Hocket hocket = addHocket(hocketForm, accountId);
        addCategoryToHocket(hocketForm, hocket);
        addThumbnailToHocket(hocketForm, hocket);

        return hocket;
    }

    private void addThumbnailToHocket(HocketForm hocketForm, Hocket hocket) {
        if(hocketForm.getThumbnailImage() != null){
            String S3Path = uploadS3.uploadImageToS3(hocketForm.getThumbnailImage(),
                    "thumbnail", String.valueOf(hocket.getId()));
            hocket.setThumbnailImage(S3Path);
        }
    }

    private void addCategoryToHocket(HocketForm hocketForm, Hocket hocket) {
        if(hocketForm.getCategoryTitles() != null){
            List<Category> categories = convertTitleToCategory(hocketForm.getCategoryTitles());
            addCategoryToHocket(hocket, categories);
        }
    }

    private void addCategoryToHocket(Hocket hocket, List<Category> categories) {
        for(Category c : categories){
            if(c != null){
                hocket.getCategories().add(c);
            }
        }
    }

    private List<Category> convertTitleToCategory( Set<String> categoryTitles) {
        return categoryTitles.stream()
                .map(t -> categoryRepository.findByTitle(t))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Hocket addHocket(HocketForm hocketForm, Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        Hocket hocket = modelMapper.map(hocketForm, Hocket.class);
        hocket.setAccount(account);
        hocket = hocketRepository.save(hocket);
        return hocket;
    }

    public List<SimpleHocketResponseDto> getSimpleinfo(Long accountId) {
        List<Hocket> hockets = hocketRepository.findByAccountId(accountId);
        if(hockets == null){
            return null;
        }

        return convertSimpleHocketToResponseDto(hockets);
    }

    private List<SimpleHocketResponseDto> convertSimpleHocketToResponseDto(List<Hocket> hockets) {
        return hockets.stream()
                .map(hocket -> makeSimpleHocketResponseDto(hocket))
                .collect(Collectors.toList());
    }

    private SimpleHocketResponseDto makeSimpleHocketResponseDto(Hocket hocket) {
        SimpleHocketResponseDto responseDto = modelMapper.map(hocket, SimpleHocketResponseDto.class);
        responseDto.setNumberOfHearts(hocket.getLikeHearts().size());
        return responseDto;
    }

    public void addImage(Long hocketId, MultipartFile multipartFile) {
        String url = uploadS3.uploadImageToS3(multipartFile, "hocketImage", String.valueOf(hocketId));

        Image image = new Image();
        image.setUrl(url);
        image.setHocket(hocketRepository.findById(hocketId).get());
        image.setAddDateTime(LocalDateTime.now());

        imageRepository.save(image);

    }

    public List<ImageResponseDto> getImageResponseDtos(Long hocketId) {
        List<Image> images = imageRepository.findByHocketIdOrderByAddDateTimeDesc(hocketId);
        List<ImageResponseDto> imageResponseDtos = convertImagesToResponseDto(images);

        return imageResponseDtos;
    }
    private List<ImageResponseDto> convertImagesToResponseDto(List<Image> images) {
        List<ImageResponseDto> imageResponseDtos = images.stream()
                .map(i -> modelMapper.map(i, ImageResponseDto.class))
                .collect(Collectors.toList());
        return imageResponseDtos;
    }


    public Hocket findById(String hocketId) {
        Optional<Hocket> byId = hocketRepository.findById(Long.valueOf(hocketId));
        return byId.orElseThrow(() -> new BadRequestException("Invalid Input"));
    }

    public HocketResponseDto makeHocketResponseDto(String hocketId) {
        Hocket hocket = findById(hocketId);
        HocketResponseDto responseDto = convertHocketToResponseDto(hocket);
        responseDto.setNumberOfHearts(hocket.likeHearts.size());

        return responseDto;
    }
    private HocketResponseDto convertHocketToResponseDto(Hocket hocket) {
        HocketResponseDto responseDto = modelMapper.map(hocket, HocketResponseDto.class);
        hocket.getCategories().stream()
                .forEach(c -> responseDto.getCategoryTitles().add(c.getTitle()));
        return responseDto;
    }

    public List<CategoryHocketResponseDto> makeHocketResponseDtoWithCategory(String categoryTitle) {
        checkExistsCategory(categoryTitle);
        List<Hocket> hockets = findHocketByCategory(categoryTitle);

        return convertHocketsToCategoryHocketDtos(hockets);
    }

    private List<CategoryHocketResponseDto> convertHocketsToCategoryHocketDtos(List<Hocket> hockets) {
        return hockets.stream()
                .map(CategoryHocketResponseDto::new)
                .collect(Collectors.toList());
    }

    private List<Hocket> findHocketByCategory(String categoryTitle) {
        return hocketRepository.findByCategory(categoryTitle);
    }

    private void checkExistsCategory(String categoryTitle) {
        if(!categoryRepository.existsByTitle(categoryTitle)){
            throw new IllegalArgumentException("존재하지 않는 카테고리 입니다.");
        }
    }
}

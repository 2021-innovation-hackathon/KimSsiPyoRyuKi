package com.hocket.modules.hocket;

import com.hocket.modules.account.AccountService;
import com.hocket.modules.category.Category;
import com.hocket.modules.category.CategoryRepository;
import com.hocket.modules.hocket.dto.CategoryHocketResponseDto;
import com.hocket.modules.hocket.dto.HocketImageRequestDto;
import com.hocket.modules.hocket.dto.HocketResponseDto;
import com.hocket.modules.hocket.dto.SimpleHocketResponseDto;
import com.hocket.modules.hocket.form.AddImageForm;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.image.Image;
import com.hocket.modules.image.ImageRepository;
import com.hocket.modules.image.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class HocketController {
    private  final HocketService hocketService;
    private final AccountService accountService;
    private final HocketRepository hocketRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;



    @PostMapping("/hocket/create")
    public ResponseEntity createHocket(@Valid HocketForm hocketForm){

        Long accountId = accountService.getAccountIdByToken(hocketForm.getToken());

        //이메일 동의가 안됐거나, 토큰이 올바르지 않거나, 회원가입이 되지 않음.
        if(accountId == null){
            return ResponseEntity.badRequest().build();
        }

        hocketService.createHocket(hocketForm, accountId);


        return ResponseEntity.ok().build();
    }

    @GetMapping("/hocket/simpleList")
    public List<SimpleHocketResponseDto> getSimpleHocketList(String token){

        Long accountId = accountService.getAccountIdByToken(token);
        if(accountId ==null){
            return null;
        }

        List<SimpleHocketResponseDto> simpleinfo = hocketService.getSimpleinfo(accountId);

        return simpleinfo;
    }

    @GetMapping("/hocket/images")
    public List<ImageResponseDto> getHocketImage(@Valid HocketImageRequestDto hocketImageRequestDto){
        Long accountId = accountService.getAccountIdByToken(hocketImageRequestDto.getToken());
        Long hocketId = Long.valueOf(hocketImageRequestDto.getHocketId());
        if(accountId == null){
            return null;
        }
        if(!isHocketConstructorEqualsRequestedUser(accountId, hocketId)){
            return null;
        }

        List<Image> images = imageRepository.findByHocketIdOrderByAddDateTimeDesc(hocketId);
        List<ImageResponseDto> imageResponseDtos = images.stream()
                .map(i -> modelMapper.map(i, ImageResponseDto.class))
                .collect(Collectors.toList());

        return imageResponseDtos;

    }



    @PostMapping("/hocket/addImage")
    public ResponseEntity addImageToHocket(@Valid AddImageForm addImageForm){
        Long accountId = accountService.getAccountIdByToken(addImageForm.getToken());
        Long hocketId = Long.valueOf(addImageForm.getHocketId());

        if(!isHocketConstructorEqualsRequestedUser(accountId, hocketId)){
            return ResponseEntity.badRequest().build();
        }

        hocketService.addImage(hocketId, addImageForm.getImage());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/hocket/details")
    public HocketResponseDto getHocketDetails(String hocketId){
        Optional<Hocket> byId = hocketRepository.findById(Long.valueOf(hocketId));

        if(byId.isEmpty()){
            return null;
        }
        Hocket hocket = byId.get();

        HocketResponseDto responseDto = modelMapper.map(hocket, HocketResponseDto.class);
        hocket.getCategories().stream()
                .forEach(c -> responseDto.getCategoryTitles().add(c.getTitle()));

        responseDto.setNumberOfHearts(hocket.likeHearts.size());

        return responseDto;
    }

    private boolean isHocketConstructorEqualsRequestedUser(Long accountId, Long hocketId) {
        return hocketRepository.findById(hocketId).get().getAccount().getId().equals(accountId);
    }

    @GetMapping("/hocket/category")
    public List<CategoryHocketResponseDto> getCategoryHocketList(String category){
        Category byTitle = categoryRepository.findByTitle(category);

        if(byTitle == null){
            return null;
        }

        List<Hocket> hockets = hocketRepository.findByCategory(category);

        List<CategoryHocketResponseDto> responseDtos = hockets.stream()
                .map(CategoryHocketResponseDto::new)
                .collect(Collectors.toList());

        return responseDtos;
    }


//    @PostMapping("/hocket/test")
//    public String S3test(@RequestParam("image") MultipartFile multipartFile){
//
//        String thumbnail = uploadS3.uploadImageToS3(multipartFile, "thumbnail", "1");
//        System.out.println(thumbnail);
//
//        return thumbnail;
//    }

}

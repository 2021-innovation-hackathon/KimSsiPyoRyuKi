package com.hocket.modules.hocket;

import com.hocket.exception.BadRequestException;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.category.Category;
import com.hocket.modules.category.CategoryRepository;
import com.hocket.modules.hocket.dto.*;
import com.hocket.modules.hocket.form.AddImageForm;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.image.Image;
import com.hocket.modules.image.ImageRepository;
import com.hocket.modules.image.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
    public ResponseEntity createHocket(@RequestBody HocketForm hocketForm, HttpServletRequest request) throws IOException {
        log.info("Client Request : ",request);
        log.info("Title : " + hocketForm.getTitle());
        Long accountId = accountService.getAccountIdByToken(hocketForm.getToken());
        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("Cline Request String : " + collect);

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

        Hocket hocket = byId.orElseThrow(() -> new BadRequestException("Invalid Input"));

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

    @GetMapping("/hocket/wishList")
    public List<WishHocketResponseDto> getWishHocketList(String token){
        return null;
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

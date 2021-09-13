package com.hocket.modules.hocket;


import com.hocket.modules.account.AccountService;
import com.hocket.modules.hocket.dto.*;
import com.hocket.modules.hocket.form.AddImageForm;
import com.hocket.modules.hocket.form.HocketForm;
import com.hocket.modules.image.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HocketController {
    private  final HocketService hocketService;
    private final AccountService accountService;
    private final HocketRepository hocketRepository;

    @PostMapping("/hocket")
    public ResponseEntity createHocket(@RequestBody HocketForm hocketForm, String token, HttpServletRequest request) throws IOException {
        Long accountId = accountService.getAccountIdByToken(token);
        hocketService.createHocket(hocketForm, accountId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/hocket/simple")
    public List<SimpleHocketResponseDto> getSimpleHocketList(String token){
        Long accountId = accountService.getAccountIdByToken(token);

        return hocketService.getSimpleinfo(accountId);
    }

    @GetMapping("/hocket/images")
    public List<ImageResponseDto> getHocketImage(@Valid HocketImageRequestDto hocketImageRequestDto) throws IllegalAccessException {
        Long accountId = accountService.getAccountIdByToken(hocketImageRequestDto.getToken());
        Long hocketId = Long.valueOf(hocketImageRequestDto.getHocketId());
        checkAuthority(accountId, hocketId);

        return hocketService.getImageResponseDtos(hocketId);
    }

    private void checkAuthority(Long accountId, Long hocketId) throws IllegalAccessException {
        if(!isHocketConstructorEqualsRequestedUser(accountId, hocketId)){
            throw new IllegalAccessException("자신의 게시물에만 접근할 수 있습니다.");
        }
    }

    @PostMapping("/hocket/images")
    public ResponseEntity addImageToHocket(@Valid AddImageForm addImageForm) throws IllegalAccessException {
        Long accountId = accountService.getAccountIdByToken(addImageForm.getToken());
        Long hocketId = Long.valueOf(addImageForm.getHocketId());
        checkAuthority(accountId, hocketId);

        hocketService.addImage(hocketId, addImageForm.getImage());

        return ResponseEntity.ok().build();
    }
    @GetMapping("/hocket/details/{hocketId}")
    public HocketResponseDto getHocketDetails(@PathVariable String hocketId){
        return hocketService.makeHocketResponseDto(hocketId);
    }

    private boolean isHocketConstructorEqualsRequestedUser(Long accountId, Long hocketId) {
        return hocketRepository.findById(hocketId).get().getAccount().getId().equals(accountId);
    }

    @GetMapping("/hocket/category/{categoryTitle}")
    public List<CategoryHocketResponseDto> getCategoryHocketList(String categoryTitle){
        return hocketService.makeHocketResponseDtoWithCategory(categoryTitle);
    }
}

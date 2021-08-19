package com.hocket.modules.hocket.form;

import com.hocket.modules.account.Account;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class HocketForm {

    @NotBlank
    private String token;

    @NotBlank
    private String title;

    @NotBlank
    private boolean isPublic;

    @NotBlank
    private boolean isAchieved;

    @NotBlank
    private boolean isRequireDate;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Integer perWeeks;

    private MultipartFile thumbnailImage;

    private String description;

    private String location;

}

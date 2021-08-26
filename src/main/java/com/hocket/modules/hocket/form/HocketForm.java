package com.hocket.modules.hocket.form;

import com.hocket.modules.account.Account;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class HocketForm {

    private String title;

    private boolean isPublic;

    private boolean isAchieved;

    private boolean isRequireDate;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Integer perWeeks;

    private MultipartFile thumbnailImage;

    private String thumbnailColor;

    private String description;

    private String hocketSpot;

    private Set<String> categoryTitles;
}

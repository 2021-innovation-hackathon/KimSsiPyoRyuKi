package com.hocket.modules.hocket.dto;

import com.hocket.modules.category.Category;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class HocketResponseDto {

    private Long id;

    private Long accountId;

    private String title;

    private String thumbnailImage;

    private String description;

    private String hocketSpot;

    private boolean isPublic;

    private boolean isRequireDate;

    private boolean isAchieved;

    private LocalDateTime createdDateTime;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Integer perWeeks;

    private Set<Category> categories = new HashSet<>();


}

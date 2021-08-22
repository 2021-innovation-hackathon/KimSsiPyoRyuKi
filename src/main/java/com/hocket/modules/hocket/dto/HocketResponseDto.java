package com.hocket.modules.hocket.dto;

import com.hocket.modules.category.Category;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class HocketResponseDto {

    private String title;

    private String description;

    private String hocketSpot;

    private boolean isPublic;

    private boolean isRequireDate;

    private boolean isAchieved;

    private LocalDateTime createdDateTime;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int perWeeks;

    private int numberOfHearts;

    private List<String> categoryTitles = new ArrayList<>();

}

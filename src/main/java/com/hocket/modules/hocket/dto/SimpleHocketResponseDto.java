package com.hocket.modules.hocket.dto;

import com.hocket.modules.hocket.Hocket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class SimpleHocketResponseDto {

    @NotBlank
    Long id;

    @NotBlank
    private String title;

    @NotBlank
    private int numberOfHearts;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private int perWeeks;

}

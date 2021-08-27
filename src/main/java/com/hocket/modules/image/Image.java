package com.hocket.modules.image;

import com.hocket.modules.hocket.Hocket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Entity @EqualsAndHashCode(of = "id")
public class Image {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String url;

    @Column(nullable = false)
    private LocalDateTime addDateTime;

    @ManyToOne
    private Hocket hocket;


}

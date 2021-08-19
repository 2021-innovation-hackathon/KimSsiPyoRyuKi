package com.hocket.modules.image;

import com.hocket.modules.hocket.Hocket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity @EqualsAndHashCode(of = "id")
public class Image {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String url;

    @ManyToOne
    private Hocket hocket;


}

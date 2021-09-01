package com.hocket.modules.video;

import com.hocket.modules.hocket.Hocket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
public class Video {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    private String url;

    @ManyToOne
    private Hocket hocket;
}
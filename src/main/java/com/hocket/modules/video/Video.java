package com.hocket.modules.video;

import com.hocket.modules.bucket.Bucket;
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
    private Long Id;

    @Column(nullable = false, length = 500)
    private String url;

    @ManyToOne
    private Bucket bucket;
}
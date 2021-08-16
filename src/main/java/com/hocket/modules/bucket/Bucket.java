package com.hocket.modules.bucket;


import com.hocket.modules.account.Account;
import com.hocket.modules.image.Image;
import com.hocket.modules.likeheart.LikeHeart;
import com.hocket.modules.tag.Tag;
import com.hocket.modules.video.Video;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@EqualsAndHashCode(of= "id")
@Entity
public class Bucket {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

//    @OneToMany(cascade = CascadeType.REMOVE)
//    private Set<Image> images = new HashSet<>();
//
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private Set<Video> videos;

    @OneToMany
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.REMOVE)
    Set<LikeHeart> likeHearts = new HashSet<>();


}

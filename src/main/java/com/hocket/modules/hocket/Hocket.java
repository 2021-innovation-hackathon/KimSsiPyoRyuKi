package com.hocket.modules.hocket;


import com.hocket.modules.account.Account;
import com.hocket.modules.likeheart.LikeHeart;
import com.hocket.modules.tag.Tag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@EqualsAndHashCode(of= "id")
@Entity
public class Hocket {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;

    @Column(nullable = false)
    private String title;

    private String thumbnailImage;

    private String description;

    private String location;

    @Column(nullable = false)
    private boolean isPublic;

    @Column(nullable = false)
    private boolean isRequireDate;

    @Column(nullable = false)
    private boolean isAchieved;

    private LocalDateTime createdDateTime;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Integer perWeeks;

    @OneToMany
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "hocket", cascade = CascadeType.REMOVE)
    Set<LikeHeart> likeHearts = new HashSet<>();

    //    @OneToMany(cascade = CascadeType.REMOVE)
//    private Set<Image> images = new HashSet<>();
//
//    @OneToMany(cascade = CascadeType.REMOVE)
//    private Set<Video> videos;


}

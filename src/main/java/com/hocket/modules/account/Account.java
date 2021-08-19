package com.hocket.modules.account;

import com.hocket.modules.likeheart.LikeHeart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String gender;

    private String ageRange;

    private String profileImage;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private Set<LikeHeart> likeHearts = new HashSet<>();





}

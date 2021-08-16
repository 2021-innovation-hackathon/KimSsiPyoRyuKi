package com.hocket.modules.likeheart;

import com.hocket.modules.account.Account;
import com.hocket.modules.bucket.Bucket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@EqualsAndHashCode(of ="id")
@Entity
public class LikeHeart {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Bucket bucket;
}

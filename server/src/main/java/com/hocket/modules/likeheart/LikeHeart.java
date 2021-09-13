package com.hocket.modules.likeheart;

import com.hocket.modules.account.Account;
import com.hocket.modules.hocket.Hocket;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
@Entity
public class LikeHeart {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Hocket hocket;

    public LikeHeart(Account account, Hocket hocket){
        this.hocket = hocket;
        this.account = account;
    }
}

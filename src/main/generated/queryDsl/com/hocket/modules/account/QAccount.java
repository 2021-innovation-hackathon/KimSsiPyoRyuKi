package com.hocket.modules.account;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -594222174L;

    public static final QAccount account = new QAccount("account");

    public final StringPath ageRange = createString("ageRange");

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<com.hocket.modules.likeheart.LikeHeart, com.hocket.modules.likeheart.QLikeHeart> likeHearts = this.<com.hocket.modules.likeheart.LikeHeart, com.hocket.modules.likeheart.QLikeHeart>createSet("likeHearts", com.hocket.modules.likeheart.LikeHeart.class, com.hocket.modules.likeheart.QLikeHeart.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}


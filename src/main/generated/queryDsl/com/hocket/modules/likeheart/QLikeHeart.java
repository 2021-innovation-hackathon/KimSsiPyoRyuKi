package com.hocket.modules.likeheart;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeHeart is a Querydsl query type for LikeHeart
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLikeHeart extends EntityPathBase<LikeHeart> {

    private static final long serialVersionUID = 713921542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeHeart likeHeart = new QLikeHeart("likeHeart");

    public final com.hocket.modules.account.QAccount account;

    public final com.hocket.modules.hocket.QHocket hocket;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QLikeHeart(String variable) {
        this(LikeHeart.class, forVariable(variable), INITS);
    }

    public QLikeHeart(Path<? extends LikeHeart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeHeart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeHeart(PathMetadata metadata, PathInits inits) {
        this(LikeHeart.class, metadata, inits);
    }

    public QLikeHeart(Class<? extends LikeHeart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.hocket.modules.account.QAccount(forProperty("account")) : null;
        this.hocket = inits.isInitialized("hocket") ? new com.hocket.modules.hocket.QHocket(forProperty("hocket"), inits.get("hocket")) : null;
    }

}


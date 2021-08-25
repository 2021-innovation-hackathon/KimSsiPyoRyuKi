package com.hocket.modules.hocket;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHocket is a Querydsl query type for Hocket
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHocket extends EntityPathBase<Hocket> {

    private static final long serialVersionUID = -1073871388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHocket hocket = new QHocket("hocket");

    public final com.hocket.modules.account.QAccount account;

    public final SetPath<com.hocket.modules.category.Category, com.hocket.modules.category.QCategory> categories = this.<com.hocket.modules.category.Category, com.hocket.modules.category.QCategory>createSet("categories", com.hocket.modules.category.Category.class, com.hocket.modules.category.QCategory.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endDateTime = createDateTime("endDateTime", java.time.LocalDateTime.class);

    public final StringPath hocketSpot = createString("hocketSpot");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAchieved = createBoolean("isAchieved");

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final BooleanPath isRequireDate = createBoolean("isRequireDate");

    public final SetPath<com.hocket.modules.likeheart.LikeHeart, com.hocket.modules.likeheart.QLikeHeart> likeHearts = this.<com.hocket.modules.likeheart.LikeHeart, com.hocket.modules.likeheart.QLikeHeart>createSet("likeHearts", com.hocket.modules.likeheart.LikeHeart.class, com.hocket.modules.likeheart.QLikeHeart.class, PathInits.DIRECT2);

    public final NumberPath<Integer> perWeeks = createNumber("perWeeks", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> startDateTime = createDateTime("startDateTime", java.time.LocalDateTime.class);

    public final StringPath thumbnailColor = createString("thumbnailColor");

    public final StringPath thumbnailImage = createString("thumbnailImage");

    public final StringPath title = createString("title");

    public QHocket(String variable) {
        this(Hocket.class, forVariable(variable), INITS);
    }

    public QHocket(Path<? extends Hocket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHocket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHocket(PathMetadata metadata, PathInits inits) {
        this(Hocket.class, metadata, inits);
    }

    public QHocket(Class<? extends Hocket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.hocket.modules.account.QAccount(forProperty("account")) : null;
    }

}


package com.hocket.modules.hocket;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class HocketRepositoryExtensionImpl extends QuerydslRepositorySupport implements HocketRepositoryExtension {

    public HocketRepositoryExtensionImpl() {
        super(Hocket.class);
    }

    public List<Hocket> findByCategory(String category){
        QHocket hocket = QHocket.hocket;

        JPQLQuery<Hocket> query = from(hocket).where(hocket.categories.any().title.containsIgnoreCase(category));

        return query.fetch();
    }

}

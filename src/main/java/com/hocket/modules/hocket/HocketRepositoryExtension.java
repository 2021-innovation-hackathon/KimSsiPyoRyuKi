package com.hocket.modules.hocket;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface HocketRepositoryExtension {
    List<Hocket> findByCategory(String category);
}

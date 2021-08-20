package com.hocket.modules.hocket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface HocketRepository extends JpaRepository<Hocket, Long> {
    List<Hocket> findByAccountId(Long accountId);
}

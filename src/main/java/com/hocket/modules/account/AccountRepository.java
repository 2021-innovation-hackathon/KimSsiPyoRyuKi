package com.hocket.modules.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}

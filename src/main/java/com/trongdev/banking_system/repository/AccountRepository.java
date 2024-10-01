package com.trongdev.banking_system.repository;

import com.trongdev.banking_system.entity.Account;
import com.trongdev.banking_system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findAllByUser(User user, Pageable pageable);
}

package com.seafta.meterhj.domain.persistence.repository.account;

import com.seafta.meterhj.domain.persistence.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByEmail(String email);
    boolean existsAccountByEmail(String email);
}

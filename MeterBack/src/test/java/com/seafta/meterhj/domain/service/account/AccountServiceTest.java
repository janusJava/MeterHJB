package com.seafta.meterhj.domain.service.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdatePasswordRequest;
import com.seafta.meterhj.helper.AccountHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void setup() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("should create account")
    void shouldCreateAccount() {
        AccountCreateRequest request = AccountHelper.buildAccountCreateRequest();
        AccountSnapshot snapshot = accountService.createAccount(request);
        Assertions.assertEquals(request.getEmail(), snapshot.getEmail());
        Assertions.assertEquals(request.getFullName(), snapshot.getFullName());
    }

    @Test
    @DisplayName("should get account")
    void shouldGetAccount() {
        Account account = accountRepository.saveAndFlush(AccountHelper.buildAccount());
        AccountDetails details = accountService.getAccount(account.getId());
        Assertions.assertEquals(account.getId(), details.getAccountId());
        Assertions.assertEquals(account.getEmail(), details.getEmail());
    }

    @Test
    @DisplayName("should update account")
    void shouldUpdateAccount() {
        Account account = accountRepository.saveAndFlush(AccountHelper.buildAccount());
        AccountUpdatedSnapshot snapshot = accountService.updateAccount(
                account.getId(),
                AccountHelper.buildAccountUpdateRequest()
        );
        Assertions.assertEquals(account.getId(), snapshot.getAccountId());
        Assertions.assertNotEquals(account.getFullName(), snapshot.getFullName());
    }

    @Test
    @DisplayName("should get account by username")
    void shouldGetAccountByUsername() {
        Account account = accountRepository.saveAndFlush(AccountHelper.buildAccount());
        Account details = accountService.getAccountByUsername(account.getEmail());
        Assertions.assertEquals(account.getEmail(), details.getEmail());
    }

    @Test
    @DisplayName("should get accounts")
    void shouldGetAccounts() {
        Account account = accountRepository.saveAndFlush(AccountHelper.buildAccount());
        Account account1 = AccountHelper.buildAccount();
        account1.setEmail("test2@seafta.com");
        accountRepository.saveAndFlush(account1);
        List<AccountDetails> details = accountService.getAccounts();
        Assertions.assertEquals(account.getEmail(), details.get(0).getEmail());
        Assertions.assertEquals(account1.getEmail(), details.get(1).getEmail());
        Assertions.assertEquals(accountRepository.count(), 2);
    }

    @Test
    @DisplayName("should delete account")
    void shouldDeleteAccount() {
        Account account = accountRepository.saveAndFlush(AccountHelper.buildAccount());
        accountService.deleteAccount(account.getId());
        Assertions.assertFalse(accountRepository.existsById(account.getId()));
    }

    @Test
    @DisplayName("should changePassword")
    void shouldChangePassword() {
        AccountSnapshot account = accountService.createAccount(AccountHelper.buildAccountCreateRequest());
        AccountUpdatePasswordRequest request = AccountHelper.buildAccountUpdatePasswordRequest();
        accountService.changePassword(account.getAccountId(), request);
        Account result = accountRepository.getOne(account.getAccountId());
        Assertions.assertNotEquals(result.getPasswordHash(), account.getPasswordHash());
    }

}

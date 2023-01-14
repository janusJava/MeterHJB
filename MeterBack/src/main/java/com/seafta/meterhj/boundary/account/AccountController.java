package com.seafta.meterhj.boundary.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdatePasswordRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdateRequest;
import com.seafta.meterhj.domain.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.security.logging.SecurityMarkers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @Override
    public AccountSnapshot createAccount(@NotNull @Valid AccountCreateRequest request) {
        log.trace("Account Controller: Creating new account {request: {}}", request);
        AccountSnapshot result = accountService.createAccount(request);
        log.debug("Account Controller: Created new account {result: {}}", result);
        return result;
    }

    @Override
    public AccountDetails getAccount(@NotNull UUID accountId) {
        log.trace("Account Controller: Getting account {accountId: {}}", accountId);
        AccountDetails result = accountService.getAccount(accountId);
        log.debug("Account Controller: Got account {accountId: {}}", accountId);
        return result;
    }

    @Override
    public List<AccountDetails> getAccounts() {
        log.trace("Account Controller: Getting account");
        List<AccountDetails> result = accountService.getAccounts();
        log.debug("Account Controller: Got accounts");
        return result;
    }

    @Override
    public AccountUpdatedSnapshot updateAccount(@NotNull UUID accountId, @NotNull @Valid AccountUpdateRequest request) {
        log.trace("Account Controller: Updating account {accountId: {}, request: {}}", accountId, request);
        AccountUpdatedSnapshot result = accountService.updateAccount(accountId, request);
        log.trace("Account Controller: Updated account {accountId: {}, result: {}}", accountId, result);
        return result;
    }

    @Override
    public void deleteAccount(@NotNull UUID accountId) {
        log.trace("Account Controller: Deleting account {accountId: {}}", accountId);
        accountService.deleteAccount(accountId);
        log.debug("Account Controller: Deleted account {accountId: {}}", accountId);
    }

    @Override
    public void changePasswordAccount(@NotNull UUID accountId, @NotNull @Valid AccountUpdatePasswordRequest request) {
        log.trace("Account Controller: Changing password {accountId: {}, request: {}}", accountId, request);
        accountService.changePassword(accountId, request);
        log.debug("Account Controller: Changed password {accountId: {}", accountId);
    }

    public Account getLoggedAccountDetails() {
        log.trace("Account Controller: Getting details about logged user");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Account result = accountService.getAccountByUsername(name);
        log.debug("Account Controller: Got details about logged user {result: {}}", result);
        return result;
    }
}

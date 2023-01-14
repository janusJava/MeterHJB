package com.seafta.meterhj.domain.service.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.mapper.account.AccountMapper;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdatePasswordRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.security.logging.SecurityMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Override
    public AccountSnapshot createAccount(@NotNull @Valid AccountCreateRequest request) {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Creating account {request: {}}", request);
        Account account = accountRepository.save(Account.buildUserAccount(request, passwordEncoder));
        AccountSnapshot result = accountMapper.toAccountSnapshot(account);
        log.debug(SecurityMarkers.CONFIDENTIAL, "Service: Created account {result: {}}", result);
        return result;
    }

    @Override
    public AccountDetails getAccount(@NotNull UUID accountId) {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Getting account {accountId: {}}", accountId);
        Account account = accountRepository.findById(accountId).get();
        AccountDetails result = accountMapper.toAccountDetails(account);
        log.debug(SecurityMarkers.CONFIDENTIAL, "Service: Got account {result: {}}", result);
        return result;
    }

    @Override
    public AccountUpdatedSnapshot updateAccount(@NotNull UUID accountId, @NotNull @Valid AccountUpdateRequest request) {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Updating account {accountId: {}, request: {}}", accountId, request);
        Account account = accountRepository.findById(accountId).get();
        AccountUpdatedSnapshot result = accountMapper.toAccountUpdatedSnapshot(account.editAccount(request));
        accountRepository.save(account);
        log.debug(SecurityMarkers.CONFIDENTIAL, "Service: Updated account {accountId: {}, result {}}", accountId, result);
        return result;
    }

    @Override
    public Account getAccountByUsername(@NotNull String username) {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Getting account by username {username: {}}", username);
        Account result = accountRepository.findByEmail(username);
        log.debug(SecurityMarkers.CONFIDENTIAL, "Service: Got account by username {username: {}, result: {}}", username, result);
        return result;
    }

    @Override
    public List<AccountDetails> getAccounts() {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Getting accounts");
        return accountRepository.findAll().stream().map(accountMapper::toAccountDetails).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(@NotNull  UUID accountId) {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Deleting account {accountId: {}}", accountId);
        accountRepository.deleteById(accountId);
        log.debug(SecurityMarkers.CONFIDENTIAL, "Service: Deleted account {accountId: {}}", accountId);
    }

    @Override
    public void changePassword(@NotNull UUID accountId, @NotNull @Valid AccountUpdatePasswordRequest request) {
    log.trace(SecurityMarkers.CONFIDENTIAL, "Service: Changing account password {accountId: {}, request: {}}", accountId, request);
    Account account = accountRepository.findById(accountId).get();
    validatePassword(account.getPasswordHash(), request);
    String newPasswordHash = passwordEncoder.encode(request.getNewPassword());
    account.setPasswordHash(newPasswordHash);
    accountRepository.save(account);
    log.debug(SecurityMarkers.CONFIDENTIAL, "Service: Changed account password {accountId: {}}", account);
    }

    private boolean validatePassword(String passwordHash, AccountUpdatePasswordRequest request) {
        log.trace("Service: Checking validate account password");
        if(passwordHash.isEmpty()) {
            log.error("Password has null value");
            throw new RuntimeException("Password has null value");
        }
        boolean status = passwordEncoder.matches(request.getOldPassword(), passwordHash);
        if(!status) {
            log.error("Given password does not match");
            throw new RuntimeException("Given password does not match");
        }
        log.debug("Service: Password validated");
        return status;
    }

    public String getNameByAccountId(UUID id) {
         Account account = accountRepository.findById(id).get();
         return account.getEmail();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Email from the request is the username
        Account account = accountRepository.findByEmail(email);
        if(account ==null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(account.getEmail(), account.getPasswordHash(), authorities);
    }
}

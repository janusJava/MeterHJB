package com.seafta.meterhj.helper;

import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdatePasswordRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdateRequest;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

public class AccountHelper {

    public static AccountCreateRequest buildAccountCreateRequest() {
        return AccountCreateRequest.builder()
                .email("test@seafta.com")
                .password("password")
                .fullName("fullName")
                .build();
    }

    public static Account buildAccount() {
        return Account.builder()
                .email("test@seafta.com")
                .passwordHash("password")
                .fullName("fullName")
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .modified(OffsetDateTime.now(Clock.systemUTC()))
                .roles(Collections.emptySet())
                .build();
    }

    public static AccountUpdateRequest buildAccountUpdateRequest() {
        return AccountUpdateRequest.builder()
                .fullName("updatedFullName")
                .build();
    }

    public static AccountUpdatePasswordRequest buildAccountUpdatePasswordRequest() {
        return AccountUpdatePasswordRequest.builder()
                .oldPassword("password")
                .newPassword("newPassword")
                .repeatPassword("newPassword")
                .build();
    }
}

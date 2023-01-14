package com.seafta.meterhj.domain.mapper.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.OffsetDateTime;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class AccountMapperTest {
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    @DisplayName("should map account to accountSnapshot")
    void shouldMapAccountToAccountSnapshot() {
        Account account = Account.builder()
                .email("test@seafta.com")
                .passwordHash("passwordHash")
                .fullName("fullName")
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .modified(OffsetDateTime.now(Clock.systemUTC()))
                .build();

        AccountSnapshot snapshot = mapper.toAccountSnapshot(account);
        Assertions.assertEquals(snapshot.getEmail(), account.getEmail());
    }

    @Test
    @DisplayName("should map account to accountDetails")
    void shouldMapAccountToAccountDetails() {
        Account account = Account.builder()
                .email("test@seafta.com")
                .passwordHash("passwordHash")
                .fullName("fullName")
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .modified(OffsetDateTime.now(Clock.systemUTC()))
                .build();

        AccountDetails details = mapper.toAccountDetails(account);
        Assertions.assertEquals(details.getEmail(), account.getEmail());
    }

    @Test
    @DisplayName("should map account to accountUpdatedSnapshot")
    void shouldMapAccountToAccountUpdatedSnapshot() {
        Account account = Account.builder()
                .email("test@seafta.com")
                .passwordHash("passwordHash")
                .fullName("fullName")
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .modified(OffsetDateTime.now(Clock.systemUTC()))
                .build();

        AccountUpdatedSnapshot snapshot = mapper.toAccountUpdatedSnapshot(account);
        Assertions.assertEquals(snapshot.getEmail(), account.getEmail());
    }
}

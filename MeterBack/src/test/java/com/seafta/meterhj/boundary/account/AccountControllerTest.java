package com.seafta.meterhj.boundary.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdatePasswordRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdateRequest;
import com.seafta.meterhj.helper.AccountHelper;
import com.seafta.meterhj.helper.MeterHelper;
import com.seafta.meterhj.helper.WebHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerTest {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL = "http://localhost:8083" + "/accounts/";

    @BeforeAll
    public void before() {
        //todo Create account object
    }

    @AfterEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create account")
    void shouldCreateAccount() {
        AccountCreateRequest request = AccountHelper.buildAccountCreateRequest();
        HttpEntity<?> httpEntity = WebHelper.assignHeaders(request);
        AccountSnapshot result = restTemplate.postForObject(BASE_URL, httpEntity, AccountSnapshot.class);
        Assertions.assertEquals(request.getFullName(), result.getFullName());
        Assertions.assertEquals(request.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("should get account")
    void shouldGetAccount() {
        Account account = repository.saveAndFlush(AccountHelper.buildAccount());
        AccountDetails result = restTemplate.getForObject(BASE_URL + account.getId(), AccountDetails.class);
        Assertions.assertEquals(account.getFullName(), result.getFullName());
        Assertions.assertEquals(account.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("should get accounts")
    void shouldGetAccountS() {
        Account account = repository.saveAndFlush(AccountHelper.buildAccount());
        Account account1 = AccountHelper.buildAccount();
        account1.setEmail("test123@seafta.com");
        repository.saveAndFlush(account1);
        ResponseEntity<List<AccountDetails>> result = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountDetails>>() {});
        Assertions.assertEquals(account.getFullName(), result.getBody().get(0).getFullName());
        Assertions.assertEquals(account1.getFullName(), result.getBody().get(1).getFullName());
        Assertions.assertEquals(account.getEmail(), result.getBody().get(0).getEmail());
        Assertions.assertEquals(account1.getEmail(), result.getBody().get(1).getEmail());
    }

    @Test
    @DisplayName("should update account")
    void shouldUpdateAccount() {
        Account account = repository.saveAndFlush(AccountHelper.buildAccount());
        AccountUpdateRequest request = AccountHelper.buildAccountUpdateRequest();
        HttpEntity<?> httpEntity = WebHelper.assignHeaders(request);
        ResponseEntity<AccountUpdatedSnapshot> result = restTemplate.exchange(BASE_URL + account.getId(), HttpMethod.PUT, httpEntity, AccountUpdatedSnapshot.class);
        Assertions.assertEquals(request.getFullName(), result.getBody().getFullName());
    }

    @Test
    @DisplayName("should update account password")
    void shouldUpdateAccountPassword() {
        Account account = AccountHelper.buildAccount();
        account.setPasswordHash(passwordEncoder.encode("password"));
        Account details = repository.save(account);
        AccountUpdatePasswordRequest request = AccountHelper.buildAccountUpdatePasswordRequest();
        HttpEntity<?> httpEntity = WebHelper.assignHeaders(request);
        ResponseEntity<Void> result = restTemplate.exchange(BASE_URL + details.getId() + "/password", HttpMethod.PUT, httpEntity, Void.class);
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}

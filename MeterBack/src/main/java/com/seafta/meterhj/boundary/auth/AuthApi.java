package com.seafta.meterhj.boundary.auth;

import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public interface AuthApi {

    @Operation(summary = "SignIn", description = "ACCOUNT_SIGN_IN_POST")
    @PostMapping( value = "/signin", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    ResponseEntity<?> authenticateUser(@RequestBody @Valid AccountLoginRequest request);

    @Operation(summary = "SignUp", description = "ACCOUNT_SIGN_UP_POST")
    @PostMapping(value = "/signup", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    AccountSnapshot registerAccount(@RequestBody @Valid AccountCreateRequest request) throws Exception;

    @Operation(summary = "logout", description = "LOGOUT")
    @GetMapping(value = "/logout")
    @ResponseStatus(NO_CONTENT)
    ResponseEntity<?> logout();
}

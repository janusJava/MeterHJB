package com.seafta.meterhj.boundary.auth;

import com.seafta.meterhj.auth.utils.JwtUtils;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountLoginRequest;
import com.seafta.meterhj.domain.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.security.logging.SecurityMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.seafta.meterhj.auth.utils.SecurityConstants.EXPIRATION_TIME;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid AccountLoginRequest request) {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Auth Controller: Authenticating user {request: {}}", request);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(request.getUsername());
        log.debug(SecurityMarkers.CONFIDENTIAL, "Auth Controller: Authenticated user {details: {}, JWT: {}}", userDetails, jwtCookie);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(userDetails.getUsername());
    }

    @Override
    public AccountSnapshot registerAccount(@RequestBody @Valid AccountCreateRequest request) throws Exception {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Auth Controller: Register account {request: {}}", request);
        if(accountRepository.existsAccountByEmail(request.getEmail())){
            throw new Exception("Email already in use");
        }
        AccountSnapshot result = accountService.createAccount(request);
        log.debug(SecurityMarkers.CONFIDENTIAL, "Auth Controller: Registered account {result: {}}", result);
        return result;
    }

    @Override
    public ResponseEntity<?> logout() {
        log.trace(SecurityMarkers.CONFIDENTIAL, "Auth Controller: Logout user");
        ResponseCookie cookie = ResponseCookie.from("access_token", "1").path("/").maxAge(0).httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("LOGOUT");
    }
}

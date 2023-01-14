package com.seafta.meterhj.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import static com.seafta.meterhj.auth.utils.SecurityConstants.EXPIRATION_TIME;
import static com.seafta.meterhj.auth.utils.SecurityConstants.SECRET;


@Component
@Slf4j
public class JwtUtils {

    @Autowired
    UserDetailsService userDetailsService;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "access_token");
        if(cookie != null) {
            return cookie.getValue();
        }
        else {
            log.error("Cookie not found");
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(String username) {
        String jwt = generateTokenFromUsername(username);
        ResponseCookie cookie = ResponseCookie.from("access_token", jwt).path("/").maxAge(EXPIRATION_TIME).httpOnly(false).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from("access_token", null).path("/").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }


    public String generateTokenFromUsername(String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withIssuer("/authenticate")
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
}

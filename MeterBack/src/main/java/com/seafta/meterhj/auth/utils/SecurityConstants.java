package com.seafta.meterhj.auth.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityConstants {

    private SecurityConstants() {throw new IllegalStateException("Utility class");}

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 90000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/registration";
}

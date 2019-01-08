package com.imyeego.exception;

import io.jsonwebtoken.Claims;

public class ExpiredException extends Exception {

    private Claims claims;
    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(String message, Claims claims) {
        super(message);
        this.claims = claims;
    }

    public Claims getClaims() {
        return claims;
    }
}

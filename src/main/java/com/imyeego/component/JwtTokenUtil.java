package com.imyeego.component;


import com.imyeego.exception.ExpiredException;
import com.imyeego.exception.MatchException;
import com.imyeego.pojo.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;



    public String getUsernameFromToken(String token) throws ExpiredException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) throws ExpiredException {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) throws ExpiredException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws ExpiredException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaimsFromToken(String token) throws ExpiredException {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
            throw new ExpiredException("token信息已过期", claims);
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) throws ExpiredException{
        final Date expiration;
        try {
            expiration = getExpirationDateFromToken(token);
        } catch (ExpiredException e) {
            throw new ExpiredException(e.getMessage());
        }

//        if (expiration.before(clock.now())){
//            throw new ExpiredException("token信息已过期");
//        }
        return false;
    }

    private Boolean isUsernameMatch(String username, String usernameFromToken) throws MatchException{
        if (!username.equals(usernameFromToken)){
            throw new MatchException("token信息与请求的用户不匹配。");
        }
        return true;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }


    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Boolean validateToken(String token, String usernameFromRequest) throws ExpiredException, MatchException {
        String usernameFromToken;
        try {
            usernameFromToken = getUsernameFromToken(token);
        } catch (ExpiredException e) {
            usernameFromToken = e.getClaims().getSubject();
        }
//        final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        System.out.println("---- usernameFromToken: " + usernameFromToken + "----");
        return (
                isUsernameMatch(usernameFromRequest, usernameFromToken)
                        && !isTokenExpired(token)
        );
    }


    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        Claims claims = null;
        try {
            claims = getAllClaimsFromToken(token);
        } catch (ExpiredException e) {
            claims = e.getClaims();
        }
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}

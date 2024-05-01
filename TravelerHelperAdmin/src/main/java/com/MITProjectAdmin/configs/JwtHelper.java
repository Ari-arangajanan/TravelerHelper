package com.MITProjectAdmin.configs;import io.jsonwebtoken.Claims;import io.jsonwebtoken.ExpiredJwtException;import io.jsonwebtoken.Jwts;import io.jsonwebtoken.SignatureAlgorithm;import io.jsonwebtoken.security.Keys;import org.springframework.security.access.AccessDeniedException;import java.security.Key;import io.jsonwebtoken.security.SignatureException;import org.springframework.security.core.userdetails.UserDetails;import java.time.Instant;import java.time.temporal.ChronoUnit;import java.util.Date;public class JwtHelper {    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);    private static final int MINUTES = 60;    public static String generateToken(String userName){        Instant now = Instant.now();        return Jwts.builder()                .subject(userName)                .issuedAt(Date.from(now))                .expiration(Date.from(now.plus(MINUTES, ChronoUnit.CENTURIES)))                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)                .compact();    }    public static String getUserName(String token){        return getTokenBody(token).getSubject();    }    private static Claims getTokenBody(String token) {        try {            return Jwts                    .parser()                    .setSigningKey(SECRET_KEY)                    .build()                    .parseSignedClaims(token)                    .getPayload();        } catch (SignatureException | ExpiredJwtException e) { // Invalid signature or expired token            throw new AccessDeniedException("Access denied: " + e.getMessage());        }    }    public static Boolean validateToken(String token, UserDetails userDetails) {        final String username = getUserName(token);        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);    }    private static boolean isTokenExpired(String token) {        Claims claims = getTokenBody(token);        return claims.getExpiration().before(new Date());    }}
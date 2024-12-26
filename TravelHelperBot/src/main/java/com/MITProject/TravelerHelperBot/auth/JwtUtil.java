package com.MITProject.TravelerHelperBot.auth;import com.MITProjectService.bot.enums.UserEnums;import io.jsonwebtoken.*;import io.jsonwebtoken.security.Keys;import jakarta.annotation.PostConstruct;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Component;import java.security.Key;import java.util.Date;import java.util.HashMap;import java.util.Map;@Componentpublic class JwtUtil {    private static Key key;    private static long EXPIRATION_TIME;    private static String DEFAULT_SECRET;    @Value("${jwt.jwtExpirationMs}")    private long expirationTime;    @Value("${jwt.secret}")    private String defaultSecret;    @PostConstruct    public void init() {        EXPIRATION_TIME = expirationTime * 12;        DEFAULT_SECRET = defaultSecret;        key = Keys.hmacShaKeyFor(DEFAULT_SECRET.getBytes());    }    public static String generateToken(long telegramId, UserEnums userType) {        Map<String, Object> claims = new HashMap<>();        claims.put("userType", userType);        claims.put("telegramId", telegramId);        return Jwts.builder()                .setClaims(claims)                .setSubject(telegramId+DEFAULT_SECRET)                .setIssuedAt(new Date())                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))                .signWith(key)                .compact();    }    public static Claims validateToken(String token) {        return Jwts.parserBuilder()                .setSigningKey(key)                .build()                .parseClaimsJws(token)                .getBody();    }    public static String getTelegramIdFromToken(String token) {        return validateToken(token).get("telegramId", String.class);    }    public static UserEnums getUserTypeFromToken(String token) {        return validateToken(token).get("userType", UserEnums.class);    }}
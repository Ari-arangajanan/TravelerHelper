package com.MITProjectAdmin.configs;import io.jsonwebtoken.Claims;import io.jsonwebtoken.Jwts;import io.jsonwebtoken.SignatureAlgorithm;import io.jsonwebtoken.io.Decoders;import io.jsonwebtoken.security.Keys;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.stereotype.Component;import java.security.Key;import java.util.Date;import java.util.HashMap;import java.util.Map;import java.util.function.Function;@Componentpublic class JwtHelper {    private static final String SECRET_KEY = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";    private static final int MINUTES = 60;    public String getUserName(String token){        return extractClaim(token, Claims::getSubject);    }    public Date extractExpiration(String token) {        return extractClaim(token, Claims::getExpiration);    }    private <T> T extractClaim(String token, Function<Claims, T> getExpiration) {        final Claims claims = extractAllClaims(token);        return getExpiration.apply(claims);    }    private Boolean isTokenExpired(String token) {        return extractExpiration(token).before(new Date());    }    public Boolean validateToken(String token, UserDetails userDetails) {        final String username = getUserName(token);        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));    }    private Claims extractAllClaims(String token) {        return Jwts.parserBuilder()                .setSigningKey(getStringKey())                .build()                .parseClaimsJws(token)                .getBody();    }    private Key getStringKey() {        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);        return Keys.hmacShaKeyFor(keyBytes);    }    public String GenerateToken(String username){        Map<String, Object> claims = new HashMap<>();        return createToken(claims, username);    }    private String createToken(Map<String, Object> claims, String username){        return Jwts.builder()                .setClaims(claims)                .setSubject(username)                .setIssuedAt(new Date(System.currentTimeMillis()))                .setExpiration(new Date(System.currentTimeMillis()+MINUTES))                .signWith(getStringKey(),SignatureAlgorithm.HS256)                .compact();    }}
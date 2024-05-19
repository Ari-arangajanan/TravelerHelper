package com.MITProjectAdmin.configs;import io.jsonwebtoken.Claims;import io.jsonwebtoken.Jwts;import io.jsonwebtoken.SignatureAlgorithm;import io.jsonwebtoken.io.Decoders;import io.jsonwebtoken.security.Keys;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.data.redis.core.StringRedisTemplate;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.stereotype.Component;import java.nio.file.attribute.UserPrincipalNotFoundException;import java.security.Key;import java.security.NoSuchAlgorithmException;import java.util.Date;import java.util.HashMap;import java.util.Map;import java.util.concurrent.TimeUnit;import java.util.function.Function;@Componentpublic class JwtHelper {    @Value("${jwt.secret}")    private String SECRET_KEY;    @Autowired    private StringRedisTemplate redisTemplate;    // for secrete key Generation//    private final String SECRET_KEY = jwt_key;    @Value("${admin.cookieExpiry}")    private static long cashedMinutes;    private static final int MINUTES = 60 * 60 * 1000;    public JwtHelper() throws NoSuchAlgorithmException {    }    public String getUserName(String token){        try {            return extractClaim(token, Claims::getSubject);        }catch (Exception e){            throw new UsernameNotFoundException(e.getMessage());        }    }    public Date extractExpiration(String token) {        return extractClaim(token, Claims::getExpiration);    }    private <T> T extractClaim(String token, Function<Claims, T> getExpiration) {        final Claims claims = extractAllClaims(token);        return getExpiration.apply(claims);    }    private Boolean isTokenExpired(String token) {        return extractExpiration(token).before(new Date());    }    public Boolean validateToken(String token, UserDetails userDetails) {        final String username = getUserName(token);        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));    }    private Claims extractAllClaims(String token) {        return Jwts.parserBuilder()                .setSigningKey(getStringKey())                .build()                .parseClaimsJws(token)                .getBody();    }    private Key getStringKey() {        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);        return Keys.hmacShaKeyFor(keyBytes);    }    public String GenerateToken(String username){        Map<String, Object> claims = new HashMap<>();        return createToken(claims, username);    }    private String createToken(Map<String, Object> claims, String username){        return Jwts.builder()                .setClaims(claims)                .setSubject(username)                .setIssuedAt(new Date(System.currentTimeMillis()))                .setExpiration(new Date(System.currentTimeMillis()+MINUTES))                .signWith(getStringKey(),SignatureAlgorithm.HS256)                .compact();    }    public boolean storeToken(String token, String username) {        try {            // Set token in Redis with username as key        redisTemplate.opsForValue().set("token:"+username, token, cashedMinutes, TimeUnit.MINUTES);        return true;        } catch (Exception e){            return false;        }    }}
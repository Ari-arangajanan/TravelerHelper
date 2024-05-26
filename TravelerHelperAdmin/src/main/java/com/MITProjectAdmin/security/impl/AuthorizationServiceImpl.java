package com.MITProjectAdmin.security.impl;import com.MITProjectAdmin.security.AuthorizationService;import com.MITProjectAdmin.security.RefreshTokenService;import com.MITProjectService.admin.domain.system.RefreshToken;import com.MITProjectService.admin.request.LoginRequest;import com.MITProjectService.exceptionhandling.RedisServerNotRunningException;import com.MITProjectService.framework.redis.RedisCashService;import com.fasterxml.jackson.core.JsonProcessingException;import jakarta.servlet.http.HttpServletResponse;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.http.HttpHeaders;import org.springframework.http.ResponseCookie;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.Authentication;import org.springframework.stereotype.Service;@Servicepublic class AuthorizationServiceImpl implements AuthorizationService {    @Value("${admin.cookieExpiry}")    private int cookieExpiry;    @Value("${admin.jwtExpirationMs}")    private int MINUTES;    @Autowired    private AuthenticationManager authenticationManager;    @Autowired    private RefreshTokenService refreshTokenService;    @Autowired    private RedisCashService redisCashService;    @Override    public Authentication authentication(LoginRequest loginRequest) {        return authenticationManager.authenticate(                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));    }    @Override    public void setRefreshTokenCookie(HttpServletResponse response, String userName, RefreshToken refreshToken) {        ResponseCookie cookie = ResponseCookie.from("refreshToken", userName + ":" + refreshToken.getRefreshToken())                .httpOnly(true)                .secure(false)                .path("/")                .maxAge(cookieExpiry/1000)                .build();        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());    }    @Override    public RefreshToken createAndSaveRefreshToken(LoginRequest loginRequest) throws JsonProcessingException {        try {            if (!loginRequest.isRememberMe()) cookieExpiry = MINUTES;            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUserName(),cookieExpiry);            // Serialize refreshToken to Redis            String serializedRefreshToken = redisCashService.serializeRefreshToken(refreshToken);            boolean isTokenSaved = redisCashService.saveValInMin("RefreshToken:"+loginRequest.getUserName()+":"+refreshToken.getRefreshToken(), serializedRefreshToken, cookieExpiry);            return refreshToken;        } catch (RedisServerNotRunningException e){            throw new RuntimeException(e.getLocalizedMessage());        }    }    @Override    public boolean saveAccessToken(String userName, String refreshToken, String accessToken) {        return redisCashService.saveValInMin("accessToken:"+userName+":"+refreshToken,accessToken, MINUTES);    }    @Override    public boolean removeToken(String key) {        return redisCashService.removeToken(key);    }}
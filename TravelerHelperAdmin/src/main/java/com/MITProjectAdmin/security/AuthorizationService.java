package com.MITProjectAdmin.security;import com.MITProjectService.admin.domain.system.RefreshToken;import com.MITProjectService.admin.request.LoginRequest;import com.fasterxml.jackson.core.JsonProcessingException;import jakarta.servlet.http.HttpServletResponse;import org.springframework.security.core.Authentication;public interface AuthorizationService {    Authentication authentication(LoginRequest loginRequest);    public void setRefreshTokenCookie(HttpServletResponse response, LoginRequest loginRequest, RefreshToken refreshToken);    RefreshToken createAndSaveRefreshToken(LoginRequest loginRequest) throws JsonProcessingException;    boolean saveAccessToken(String userName, String refreshToken, String accessToken);    boolean removeToken(String key);}
package com.MITProjectAdmin.security.impl;import com.MITProjectAdmin.security.RefreshTokenService;import com.MITProjectService.admin.domain.system.RefreshToken;import com.MITProjectService.admin.domain.system.SysUser;import com.MITProjectService.admin.service.SysUserService;import com.MITProjectService.exceptionhandling.TokenRefreshException;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.stereotype.Service;import java.time.Instant;import java.util.Optional;import java.util.UUID;@Servicepublic class RefreshTokenServiceImpl implements RefreshTokenService {    @Value("${admin.jwtRefreshExpirationMs}")    private Long refreshTokenDurationMs;    @Autowired    private SysUserService sysUserService;    @Override    public RefreshToken createRefreshToken(String username) {        RefreshToken refreshToken = new RefreshToken();        SysUser sysUser = sysUserService.findByUserName(username);                if(sysUser == null){                    throw new UsernameNotFoundException("User not found");                }//        refreshToken.setUser(sysUser);        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));        refreshToken.setRefreshToken(UUID.randomUUID().toString());        return refreshToken;    }    @Override    public Optional<RefreshToken> findByToken(String token) {//        return refreshTokenRepository.findByToken(token);        return Optional.empty();    }    @Override    public RefreshToken verifyExpiration(RefreshToken token) {        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {//            refreshTokenRepository.delete(token);            throw new TokenRefreshException(token.getRefreshToken(), "Refresh token was expired. Please make a new sign-in request");        }        return token;    }    @Override    public void deleteByUserId(String userName) {//        refreshTokenRepository.deleteByUser(sysUserService.findByUserName(userName));    }}
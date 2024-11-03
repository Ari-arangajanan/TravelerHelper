package com.MITProjectAdmin.security.impl;import com.MITProjectAdmin.Controller.system.SystemUserController;import com.MITProjectAdmin.configs.MyUserDetails;import com.MITProjectAdmin.security.RefreshTokenService;import com.MITProjectAdmin.security.UserService;import com.MITProjectService.admin.dao.AuthoritiesRepo;import com.MITProjectService.admin.dao.SystemUserRepo;import com.MITProjectService.admin.domain.system.Authorities;import com.MITProjectService.admin.domain.system.RefreshToken;import com.MITProjectService.admin.domain.system.SysUser;import com.MITProjectService.admin.request.LoginRequest;import com.MITProjectService.framework.redis.RedisCashService;import com.fasterxml.jackson.core.JsonProcessingException;import io.jsonwebtoken.lang.Collections;import jakarta.servlet.http.HttpServletResponse;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.http.HttpHeaders;import org.springframework.http.ResponseCookie;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.Authentication;import org.springframework.security.core.GrantedAuthority;import org.springframework.security.core.authority.SimpleGrantedAuthority;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.stereotype.Service;import java.util.Collection;import java.util.List;import java.util.stream.Collectors;@Servicepublic class UserServiceImpl implements UserDetailsService , UserService {    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);    @Autowired    private SystemUserRepo systemUserRepo;    @Override    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {        SysUser sysUser = systemUserRepo.findByUserName(username);        if (sysUser == null) throw new UsernameNotFoundException("SnUser not found with username: " + username);        return new MyUserDetails(sysUser);    }}
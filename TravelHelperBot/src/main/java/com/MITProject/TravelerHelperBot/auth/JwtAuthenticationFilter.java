package com.MITProject.TravelerHelperBot.auth;import io.jsonwebtoken.Claims;import jakarta.servlet.FilterChain;import jakarta.servlet.ServletException;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.authority.SimpleGrantedAuthority;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.stereotype.Component;import org.springframework.web.filter.OncePerRequestFilter;import java.io.IOException;import java.util.Collections;@Componentpublic class JwtAuthenticationFilter extends OncePerRequestFilter {    @Override    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {        String authHeader = request.getHeader("Authorization");        if (authHeader != null && authHeader.startsWith("Bearer ")) {            String token = authHeader.substring(7);            try {                Claims claims = JwtUtil.validateToken(token);                long telegramId = Long.parseLong(claims.get("telegramId").toString());                String role = claims.get("userType").toString();                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.toUpperCase());                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(telegramId,null, Collections.singletonList(authority));                SecurityContextHolder.getContext().setAuthentication(authentication);            } catch (Exception e) {                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);                return;            }        }        filterChain.doFilter(request, response);    }}
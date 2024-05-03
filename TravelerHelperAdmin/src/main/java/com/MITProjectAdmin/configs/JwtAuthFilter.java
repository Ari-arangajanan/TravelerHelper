package com.MITProjectAdmin.configs;import com.MITProjectAdmin.security.UserService;import com.MITProjectAdmin.security.impl.UserServiceImpl;import jakarta.servlet.FilterChain;import jakarta.servlet.ServletException;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.security.web.authentication.WebAuthenticationDetails;import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;import org.springframework.stereotype.Component;import org.springframework.web.filter.OncePerRequestFilter;import java.io.IOException;@Componentpublic class JwtAuthFilter extends OncePerRequestFilter {    @Autowired    private JwtHelper jwtHelper;    @Autowired    private UserServiceImpl userServiceImpl;    @Override    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {        String authHeader = request.getHeader("Authorization");        String userName = null;        String token = null;        if (authHeader != null && authHeader.startsWith("filterToken ")) {            token = authHeader.substring(11);            userName = jwtHelper.getUserName(token);        }        if (userName != null && SecurityContextHolder.getContext().getAuthentication() ==null){            UserDetails userDetails = userServiceImpl.loadUserByUsername(userName);            if (jwtHelper.validateToken(token,userDetails)){                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(                        userDetails,null, userDetails.getAuthorities()                );                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));                SecurityContextHolder.getContext().setAuthentication(authenticationToken);            }        }        filterChain.doFilter(request,response);    }}
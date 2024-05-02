package com.MITProjectAdmin.configs;import com.MITProjectAdmin.security.UserService;import com.MITProjectService.admin.service.SysUserService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.http.HttpMethod;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.dao.DaoAuthenticationProvider;import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.password.NoOpPasswordEncoder;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.provisioning.JdbcUserDetailsManager;import org.springframework.security.provisioning.UserDetailsManager;import org.springframework.security.web.SecurityFilterChain;import javax.sql.DataSource;@Configuration@EnableWebSecuritypublic class SecurityConfig {    private final UserService userService;    @Autowired    public SecurityConfig(SysUserService sysUserService, UserService userService) {        this.userService = userService;    }    @Bean    public DaoAuthenticationProvider authenticationProvider(UserDetailsManager userDetailsManager){        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();        authenticationProvider.setUserDetailsService(userDetailsManager);        authenticationProvider.setPasswordEncoder(passwordEncoder());        return authenticationProvider;    }   @Bean   public UserDetailsManager userDetailsManager(DataSource dataSource){       JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);       jdbcUserDetailsManager.setUsersByUsernameQuery("select username , password, status from system_user where username=?");       jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_name , role from authorities where user_name =?");       return jdbcUserDetailsManager;   }    @Bean    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,AuthenticationManager authenticationManager) throws Exception{        httpSecurity.authorizeRequests(                configure->configure                        .requestMatchers(HttpMethod.POST,"/admin/systemUser/add").hasRole("ADMIN")                        .requestMatchers(HttpMethod.GET,"/api/v1/hello/hello").hasAnyRole("ADMIN","EMPLOYEE")                        .requestMatchers(HttpMethod.PUT,"/admin/systemUser/**").hasRole("ADMIN") //** for put url /admin/systemUser/{id}                        .requestMatchers(HttpMethod.DELETE,"/admin/systemUser/add").hasRole("ADMIN")                        .requestMatchers(HttpMethod.PUT,"/admin/systemUser/add").hasRole("ADMIN")                        .requestMatchers(HttpMethod.POST,"/admin/systemUser/login").permitAll()                        .requestMatchers(HttpMethod.POST,"/admin/systemUser/register").permitAll()                        .anyRequest().authenticated()        ).authenticationManager(authenticationManager).csrf(AbstractHttpConfigurer::disable);        return httpSecurity.build();    }    @Bean    public PasswordEncoder passwordEncoder() {        return new BCryptPasswordEncoder(); // Using a strong password encoder    }    @Bean    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity)throws Exception{        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);        authenticationManagerBuilder.userDetailsService((UserDetailsService) userService).passwordEncoder(passwordEncoder());        return authenticationManagerBuilder.build();    }}
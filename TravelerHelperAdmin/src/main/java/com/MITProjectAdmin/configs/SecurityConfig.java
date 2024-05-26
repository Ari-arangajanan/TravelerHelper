package com.MITProjectAdmin.configs;import com.MITProjectAdmin.security.UserService;import com.MITProjectAdmin.security.impl.UserServiceImpl;import lombok.AllArgsConstructor;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.http.HttpMethod;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.AuthenticationProvider;import org.springframework.security.authentication.dao.DaoAuthenticationProvider;import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;import org.springframework.security.config.http.SessionCreationPolicy;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.web.AuthenticationEntryPoint;import org.springframework.security.web.SecurityFilterChain;import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;import static org.springframework.security.config.Customizer.withDefaults;@Configuration@EnableWebSecurity@EnableMethodSecurity@AllArgsConstructorpublic class SecurityConfig{    @Autowired    private UserService userService;    @Autowired    private JwtAuthFilter jwtAuthFilter;    @Bean    public UserDetailsService userDetailsService() {        return new UserServiceImpl();    }    @Bean    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {        return httpSecurity.csrf(AbstractHttpConfigurer::disable)                .cors(withDefaults())                .authorizeHttpRequests(auth -> auth                        .requestMatchers(HttpMethod.POST, "/admin/systemUser/login").permitAll()                        .requestMatchers(HttpMethod.POST, "/admin/systemUser/logout").permitAll()                        .requestMatchers("/admin/systemUser/**").authenticated()                        .requestMatchers(HttpMethod.POST, "/admin/systemUser/register").hasRole("ADMIN")                        .requestMatchers(HttpMethod.GET, "/api/v1/hello/hello").hasAnyRole("ADMIN", "EMPLOYEE")                        .requestMatchers(HttpMethod.PUT, "/admin/systemUser/**").hasRole("ADMIN") //** for put url /admin/systemUser/{id}                        .requestMatchers(HttpMethod.DELETE, "/admin/systemUser/add").hasRole("ADMIN")                        .requestMatchers(HttpMethod.PUT, "/admin/systemUser/add").hasRole("ADMIN")                )                .sessionManagement(session -> session                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))                .exceptionHandling(exception -> exception                        .authenticationEntryPoint(authenticationEntryPoint())) // Ensure 401 is returned                .authenticationProvider(authenticationProvider())                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)                .build();    }    @Bean    public PasswordEncoder passwordEncoder() {        return new BCryptPasswordEncoder(); // Using a strong password encoder    }    @Bean    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {        return config.getAuthenticationManager();    }    @Bean    public AuthenticationProvider authenticationProvider() {        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();        authenticationProvider.setUserDetailsService(userDetailsService());        authenticationProvider.setPasswordEncoder(passwordEncoder());        return authenticationProvider;    }    @Bean    public AuthenticationEntryPoint authenticationEntryPoint() {        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();        entryPoint.setRealmName("TravelBot");        return entryPoint;    }}
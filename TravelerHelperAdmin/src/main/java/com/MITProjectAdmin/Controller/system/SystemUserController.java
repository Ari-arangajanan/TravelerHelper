package com.MITProjectAdmin.Controller.system;import com.MITProjectAdmin.configs.JwtHelper;import com.MITProjectAdmin.configs.JwtResponseDTO;import com.MITProjectAdmin.security.AuthorizationService;import com.MITProjectService.admin.domain.system.RefreshToken;import com.MITProjectService.admin.domain.system.SysUser;import com.MITProjectService.admin.request.LoginRequest;import com.MITProjectService.admin.service.SysUserService;import com.MITProjectService.admin.vo.SystemUserVo;import com.MITProjectService.exceptionhandling.DataTransactionalException;import jakarta.servlet.http.Cookie;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import jakarta.servlet.http.HttpSession;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.dao.DataIntegrityViolationException;import org.springframework.data.domain.Page;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.security.authentication.BadCredentialsException;import org.springframework.security.core.Authentication;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;@CrossOrigin@RestController@RequestMapping(path = "/admin/systemUser")public class SystemUserController {    @Value("${admin.cookieExpiry}")    private long cookieExpiry;    private static final long MINUTES = 60;    Logger logger = LoggerFactory.getLogger(SystemUserController.class);    @Autowired    public SystemUserController(SysUserService systemUserService, JwtHelper jwtHelper, PasswordEncoder passwordEncoder, AuthorizationService authorizationService) {        this.systemUserService = systemUserService;        this.jwtHelper = jwtHelper;        this.passwordEncoder = passwordEncoder;        this.authorizationService = authorizationService;    }    private final SysUserService systemUserService;    private final JwtHelper jwtHelper;    private final PasswordEncoder passwordEncoder;    private final AuthorizationService authorizationService;    @PostMapping(value = "/login")    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {        try {            Authentication authentication = authorizationService.authentication(loginRequest);            if (authentication.isAuthenticated()) {                RefreshToken refreshToken = authorizationService.createAndSaveRefreshToken(loginRequest);                String accessToken = jwtHelper.GenerateToken(loginRequest.getUserName());                boolean isAccessTokenSaved = authorizationService.saveAccessToken(loginRequest.getUserName(),refreshToken.getRefreshToken(),accessToken);                authorizationService.setRefreshTokenCookie(response,loginRequest.getUserName(), refreshToken);                JwtResponseDTO jwtResponse = JwtResponseDTO.builder()                        .accessToken(accessToken)                        .refreshToken(refreshToken.getRefreshToken())                        .userName(loginRequest.getUserName())                        .build();                return ResponseEntity.ok(jwtResponse);            } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");        } catch (BadCredentialsException | UsernameNotFoundException e) {            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());        } catch (Exception e) {            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());        }    }    @PostMapping(value = "/logout")    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {        for (Cookie cookie : request.getCookies()){            if (cookie.getName().equals("refreshToken")){                String accessToken = cookie.getValue();                String[] splitTxt = accessToken.split(":");                boolean isRemoved = authorizationService.removeToken("RefreshToken:"+ splitTxt[0]);                if (!isRemoved) throw new RuntimeException("errorInRemovingRefreshToken");            }        }        SecurityContextHolder.clearContext();        // Remove cookies        Cookie[] cookies = request.getCookies();        if (cookies != null) {            for (Cookie cookie : cookies) {                cookie.setMaxAge(0);                cookie.setValue(null);                cookie.setPath("/");                response.addCookie(cookie);            }        }        return ResponseEntity.ok("logout");    }    @RequestMapping(value = "/index", method = RequestMethod.POST)    public Page<SystemUserVo> index(HttpServletRequest request, HttpServletResponse response) {        return systemUserService.findAll(request,response);    }    @PostMapping(value = "/register")    public ResponseEntity<?> register(@RequestBody SysUser sysUser) {        try {            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));            SysUser isSaved = systemUserService.addSysUser(sysUser);            return new ResponseEntity<>(isSaved,HttpStatus.CREATED);        }catch (DataIntegrityViolationException e){            if (e.getRootCause() != null &&  e.getRootCause().getMessage().contains("Duplicate entry")){                throw new DataTransactionalException("The username already exists. Please choose a different username.");            }else throw new DataTransactionalException(e.getLocalizedMessage());        }        catch (Exception e){            throw new DataTransactionalException(e.getLocalizedMessage());        }    }    @PutMapping(value = "/updateUser")    public ResponseEntity<SysUser> updateUser(@RequestBody SysUser sysUser, HttpSession httpSession) {        // todo implement the rest        return ResponseEntity.ok(sysUser);    }    @RequestMapping(value = "/list", method = RequestMethod.GET)    public String list() {        return "list";    }}
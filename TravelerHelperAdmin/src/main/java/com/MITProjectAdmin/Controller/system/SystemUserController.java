package com.MITProjectAdmin.Controller.system;import com.MITProjectAdmin.configs.JwtHelper;import com.MITProjectService.admin.domain.system.SysUser;import com.MITProjectService.admin.request.LoginRequest;import com.MITProjectService.admin.service.SysUserService;import com.MITProjectService.admin.vo.LoginResponse;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;@RestController@RequestMapping(path= "/admin/systemUser")public class SystemUserController {    Logger logger = LoggerFactory.getLogger(SystemUserController.class);    @Autowired    public SystemUserController(SysUserService systemUserService, AuthenticationManager authenticationManager) {        this.systemUserService = systemUserService;        this.authenticationManager = authenticationManager;    }    private final SysUserService systemUserService;    private final AuthenticationManager authenticationManager;    @PostMapping(value = "/login")    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){        try {            Authentication authentication = authenticationManager.authenticate(                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())            );            String token = JwtHelper(loginRequest.getUserName());            return ResponseEntity.ok(new LoginResponse(loginRequest.getUserName(), token));        } catch (Exception e) {            // Handle authentication exception (e.g., BadCredentialsException)            return ResponseEntity.badRequest().body(new LoginResponse());        }    }    @GetMapping(value = "/logout")    public String logout(){        return "logout";    }    @RequestMapping(value = "/index", method = RequestMethod.POST)    public String index(){        return "index";    }    @PostMapping(value = "/register")    public ResponseEntity<Void> register(@RequestBody SysUser sysUser){        systemUserService.addSysUser(sysUser);        return ResponseEntity.status(HttpStatus.CREATED).build();    }    @RequestMapping(value = "/list", method = RequestMethod.GET)    public String list(){        return "list";    }}
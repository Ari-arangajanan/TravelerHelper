package com.MITProject.TravelerHelperBot.botController;import com.MITProject.TravelerHelperBot.auth.JwtUtil;import com.MITProjectService.bot.Services.SnUserService;import com.MITProjectService.bot.domain.SnUser;import com.MITProjectService.bot.enums.UserEnums;import lombok.RequiredArgsConstructor;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import java.util.HashMap;import java.util.Map;import java.util.Optional;@RestController@RequestMapping(value = "api/auth")@RequiredArgsConstructor@CrossOrigin(origins = {"https://2eru7awa8f7p.share.zrok.io","http://localhost:5175"}, allowedHeaders = "*")public class AuthController {    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);    private final SnUserService snUserService;    @PostMapping(value = "generateToken")    public ResponseEntity<Map<String, String>> generateToken(@RequestBody Map<String, String> request) {        Long telegram_id = Long.parseLong(request.get("telegram_id"));        Optional<SnUser> user = snUserService.getUserByTelegrmId(telegram_id);        Map<String, String> response = new HashMap<>();        if (user.isPresent()) {            // generate token            SnUser snUser = user.get();            String token = JwtUtil.generateToken(snUser.getTelegramId(), UserEnums.fromVal(snUser.getType()));            response.put("token", token);            return ResponseEntity.ok(response);        }else {            response.put("error", "User not found");            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);        }    }}
package com.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.dto.CredentialDto;
import com.model.dto.TokenSetDto;
import com.model.table.User;
import com.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.common.Constants.BEARER;
import static com.common.Constants.REFRESH_TOKEN;


@RestController
@RequestMapping("/credentials")
public class CredentialController {
    @Autowired
    private CredentialService credService;
    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok().body(credService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(credService.findById(id));
    }
    @GetMapping("/verify")
    public ResponseEntity<?> checkAccess(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok().body(credService.verify(authorization));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDto cred,HttpServletResponse response) {
        TokenSetDto tokens = credService.login(cred);
        String refreshToken=tokens.getRefreshToken();
        tokens.setRefreshToken(null);
        HttpCookie refreshCookie = ResponseCookie
                .from(REFRESH_TOKEN, refreshToken)
                .path("/")
                .secure(true)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()+";HttpOnly;")
                .body(tokens);
    }
    @GetMapping("/set-cookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("myCookie", "cookieValue");
        cookie.setPath("/");
        response.addCookie(cookie);
        return "Cookie set successfully!";
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authorization,@CookieValue(name = REFRESH_TOKEN) String inputRefreshToken) throws JsonProcessingException {
        TokenSetDto tokens = credService.refresh(new TokenSetDto(authorization.replace(BEARER+" ",""),inputRefreshToken));
        String refreshToken=tokens.getRefreshToken();
        tokens.setRefreshToken(null);
        HttpCookie refreshCookie = ResponseCookie
                .from(REFRESH_TOKEN, refreshToken)
                .path("/")
                .secure(true)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()+";HttpOnly;")
                .body(tokens);

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User User) {
        return ResponseEntity.ok().body(credService.update(id, User));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        credService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully...!");
    }
}


package com.example.smartretail.controller;

import com.example.smartretail.dto.AuthRequest;
import com.example.smartretail.dto.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Map<String, String> userDatabase = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        if (userDatabase.containsKey(request.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) 
                    .body(new AuthResponse("Username đã tồn tại trên hệ thống!"));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        userDatabase.put(request.getUsername(), encodedPassword);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse("Đăng ký tài khoản thành công!"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String encodedPassword = userDatabase.get(request.getUsername());

        if (encodedPassword == null || !passwordEncoder.matches(request.getPassword(), encodedPassword)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Sai tài khoản hoặc mật khẩu!"));
        }

        return ResponseEntity
                .ok(new AuthResponse("Đăng nhập thành công!"));
    }
}
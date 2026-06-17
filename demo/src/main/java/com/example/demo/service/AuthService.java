package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.RegisterResponse;
public interface AuthService {
    LoginResponse handleLogin(LoginRequest loginRequest);
    RegisterResponse handleRegister(RegisterRequest registerRequest);
}
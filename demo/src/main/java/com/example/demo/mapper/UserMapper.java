package com.example.demo.mapper;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
public interface UserMapper {
    UserLoginDto toUserLogin(Object userPrincipal);
    User toUserRegister(RegisterRequest registerRequest, String encodedPassword);
    RegisterResponse toRegisterResponse(User user);
}
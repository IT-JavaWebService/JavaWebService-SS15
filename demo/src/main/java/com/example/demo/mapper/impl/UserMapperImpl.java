package com.example.demo.mapper.impl;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserLoginDto toUserLogin(Object userPrincipal) {
        if (userPrincipal instanceof UserDetailsImpl userDetails) {
            return UserLoginDto.builder()
                    .id(userDetails.getId())
                    .email(userDetails.getEmail())
                    .username(userDetails.getUsername())
                    .fullName(userDetails.getFullName())
                    .roles(userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toSet()))
                    .lastLogin(LocalDateTime.now())
                    .build();
        }
        return null;
    }
    @Override
    public User toUserRegister(RegisterRequest registerRequest, String encodedPassword) {
        if (registerRequest == null) {
            return null;
        }
        return User.builder()
                .username(registerRequest.getUsername())
                .password(encodedPassword) // Receive encoded password
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .enabled(true) // Default when register
                .userRoles(new HashSet<>())
                .build();
    }
    @Override
    public RegisterResponse toRegisterResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roleNames = (user.getUserRoles() == null)
                ? Collections.emptySet()
                : user.getUserRoles().stream()
                  .filter(ur -> ur.getRole() != null) // Additional protection if the role is null
                  .map(ur -> ur.getRole().getName())
                  .collect(Collectors.toSet());
        UserRegisterDto userDetail = UserRegisterDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(roleNames) // Assign the list of safely processed items
                .createdAt(java.time.LocalDateTime.now())
                .build();
        return RegisterResponse.builder()
                .status("success")
                .message("User registered successfully!")
                .userRegisterDto(userDetail)
                .build();
    }
}
package com.example.demo.service.impl;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UsernameAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    @Lazy
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public LoginResponse handleLogin(LoginRequest loginRequest) {
        // 1. Perform authentication through AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // 2. Save the credentials to the Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 3. Retrieve authenticated user information from the authentication object
        Object principal = authentication.getPrincipal();
        // 4. Retrieve additional user information from the principal
        UserLoginDto userLoginDto = userMapper.toUserLogin(principal);
        // 5. Return LoginResponse
        return LoginResponse.builder()
                .status("success")
                .message("Login successfully!")
                .userLoginDto(userLoginDto)
                .build();
    }
    @Transactional
    @Override
    public RegisterResponse handleRegister(RegisterRequest registerRequest) {
        // 1. Validate user exists or not
        if (userRepository.existsUserByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(registerRequest.getUsername());
        }
        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException(registerRequest.getEmail());
        }
        // 2. Encode password at Service
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        // 3. Use Mapper to mapping from dto to entity
        User user = userMapper.toUserRegister(registerRequest, encodedPassword);
        // 4. Find the default role from the database.
        Role defaultRole = roleRepository.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_USER not found."));
        // 5. Create a UserRole middle object
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(defaultRole)
                .grantedBy("SYSTEM")
                .grantedAt(Instant.now())
                .build();
        // 6. Assign a bidirectional link (Important for JPA to understand and save to the database)
        // -> Gán liên kết hai chiều (Quan trọng để JPA hiểu và lưu xuống DB)
        user.getUserRoles().add(userRole);
        // 7. Save to database and return saved entity
        User savedUser = userRepository.save(user);
        return userMapper.toRegisterResponse(savedUser);
    }
}
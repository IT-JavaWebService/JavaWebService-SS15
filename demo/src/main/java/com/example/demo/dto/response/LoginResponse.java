package com.example.demo.dto.response;

import com.example.demo.dto.UserLoginDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginResponse {
    private String status;
    private String message;
    private UserLoginDto userLoginDto;
}
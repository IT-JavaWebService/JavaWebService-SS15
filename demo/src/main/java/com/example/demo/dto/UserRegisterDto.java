package com.example.demo.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Set<String> roles;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
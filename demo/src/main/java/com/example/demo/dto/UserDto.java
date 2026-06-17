package com.example.demo.dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
}
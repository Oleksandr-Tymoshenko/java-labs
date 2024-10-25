package com.example.webstore.dto.user;

import lombok.Data;

@Data
public class RegisterUserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}

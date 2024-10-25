package com.example.webstore.dto.user;

import com.example.webstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(fields = {"password", "repeatPassword"})
public class RegisterUserDto {
    @NotBlank
    @Length(min = 2, max = 255)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 255)
    private String lastName;

    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Length(max = 255)
    private String email;

    private String phone;

    private String address;

    @Length(min = 8, max = 255)
    private String password;

    @Length(min = 8, max = 255)
    private String repeatPassword;
}

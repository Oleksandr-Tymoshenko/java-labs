package com.example.webstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateOrderRequestDto {
    @NotBlank
    @Length(min = 2, max = 255)
    private String shippingAddress;
    @NotBlank
    private String phone;
    private boolean saveUserInfo;
}

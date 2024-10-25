package com.example.webstore.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductDto {
    @NotBlank
    private String name;

    private String image;

    private String description;
    @NotBlank
    private double price;
}

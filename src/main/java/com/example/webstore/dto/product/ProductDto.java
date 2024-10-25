package com.example.webstore.dto.product;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    private String name;

    private String image;

    private String description;

    private double price;

    private LocalDateTime createdAt;
}

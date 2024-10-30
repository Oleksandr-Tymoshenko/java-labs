package com.example.webstore.mapper;

import com.example.webstore.dto.product.CreateProductDto;
import com.example.webstore.dto.product.ProductDto;
import com.example.webstore.model.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProductMapper {
    Product toProduct(CreateProductDto productDto);

    ProductDto toProductDto(Product product);
}

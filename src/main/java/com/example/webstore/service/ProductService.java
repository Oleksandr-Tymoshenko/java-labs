package com.example.webstore.service;

import com.example.webstore.dto.product.CreateProductDto;
import com.example.webstore.dto.product.ProductDto;
import com.example.webstore.mapper.ProductMapper;
import com.example.webstore.model.Product;
import com.example.webstore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(Double minPrice, Double maxPrice) {
        List<Product> products;
        if (minPrice != null && maxPrice != null) {
            products = productRepository.findAllByPriceBetween(minPrice, maxPrice);
        } else if (minPrice != null) {
            products = productRepository.findAllByPriceGreaterThanEqual(minPrice);
        } else if (maxPrice != null) {
            products = productRepository.findAllByPriceLessThanEqual(maxPrice);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().map(productMapper::toProductDto).toList();
    }

    public ProductDto getProductById(Long id) {
        return productMapper.toProductDto(
                productRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Can't find product by id " + id))
        );
    }

    public void createProduct(CreateProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        product.setCreatedAt(LocalDateTime.now());
        productMapper.toProductDto(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> getRecentProducts() {
        Pageable pageable = PageRequest.of(0, 4);
        return productRepository.getRecentProducts(pageable)
                .stream()
                .map(productMapper::toProductDto)
                .toList();
    }
}

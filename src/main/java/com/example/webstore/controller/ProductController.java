package com.example.webstore.controller;

import com.example.webstore.dto.product.CreateProductDto;
import com.example.webstore.dto.product.ProductDto;
import com.example.webstore.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getProducts(@RequestParam(value = "minPrice", required = false) Double minPrice,
                              @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                              Model model) {
        List<ProductDto> products = productService.getAllProducts(minPrice, maxPrice);
        model.addAttribute("products", products);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "products";
    }

    @GetMapping("/{productId}")
    public String getProductById(@PathVariable Long productId, Model model) {
        ProductDto product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createProduct(@ModelAttribute CreateProductDto productDto) {
        productService.createProduct(productDto);
        return "redirect:/products";
    }

    @PostMapping("/{productId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products?deleted=true";
    }
}

package com.example.webstore.controller;

import com.example.webstore.dto.product.ProductDto;
import com.example.webstore.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping({"", "/"})
    public String home(Model model) {
        List<ProductDto> recentProducts = productService.getRecentProducts();
        model.addAttribute("recentProducts", recentProducts);
        return "index";
    }
}

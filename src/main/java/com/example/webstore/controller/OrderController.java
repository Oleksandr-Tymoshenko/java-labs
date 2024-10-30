package com.example.webstore.controller;

import static com.example.webstore.util.Constants.ORDER_SUCCESS_PAGE;

import com.example.webstore.dto.order.CreateOrderRequestDto;
import com.example.webstore.model.User;
import com.example.webstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public String placeOrder(Model model,
                             Authentication authentication,
                             @Valid @ModelAttribute CreateOrderRequestDto createOrderRequestDto,
                             @AuthenticationPrincipal User user) {
        orderService.placeOrder(user.getId(), authentication, createOrderRequestDto);
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        return ORDER_SUCCESS_PAGE;
    }
}

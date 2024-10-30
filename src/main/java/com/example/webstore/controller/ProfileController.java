package com.example.webstore.controller;

import static com.example.webstore.util.Constants.PROFILE_PAGE;

import com.example.webstore.dto.order.OrderDto;
import com.example.webstore.model.User;
import com.example.webstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final OrderService orderService;

    @GetMapping
    public String getProfilePage(Model model, @AuthenticationPrincipal User user,
                                 Pageable pageable) {
        List<OrderDto> orders = orderService.getOrders(user.getId(), pageable);
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        return PROFILE_PAGE;
    }
}

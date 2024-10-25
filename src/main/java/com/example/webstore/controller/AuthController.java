package com.example.webstore.controller;

import static com.example.webstore.util.Constants.ERROR_PARAM;
import static com.example.webstore.util.Constants.LOGIN_FORM_PAGE;
import static com.example.webstore.util.Constants.REGISTER_FORM_PAGE;

import com.example.webstore.dto.user.RegisterUserDto;
import com.example.webstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return LOGIN_FORM_PAGE;
    }

    @GetMapping("/sign-up")
    public String registerForm(Model model) {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        model.addAttribute(registerUserDto);
        model.addAttribute(ERROR_PARAM, null);
        return REGISTER_FORM_PAGE;
    }

    @PostMapping("/sign-up")
    public String register(Model model,
                           @Valid @ModelAttribute RegisterUserDto registerUserDto,
                           BindingResult result
    ) {
        if (result.hasErrors()) {
            return REGISTER_FORM_PAGE;
        }

        try {
            userService.register(registerUserDto);
        } catch (Exception e) {
            model.addAttribute(ERROR_PARAM, e.getMessage());
        }
        if (result.hasErrors()) {
            return REGISTER_FORM_PAGE;
        }

        return "redirect:/auth/login?registered=true";
    }
}

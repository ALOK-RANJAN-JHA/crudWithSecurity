package com.crud.controller;

import com.crud.dto.UserDto;
import com.crud.entity.User;
import com.crud.repository.UserRepository;
import com.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto dto, BindingResult br, Model model) {
        if (br.hasErrors()) return "register";
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        User u = User.builder().username(dto.getUsername()).password(dto.getPassword()).build();
        userService.register(u);
        return "redirect:/login?registered=true";
    }
}


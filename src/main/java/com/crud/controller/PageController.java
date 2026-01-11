package com.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String root() { return "redirect:/login"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/employees")
    public String employees() { return "employees"; }

    @GetMapping("/employee-form")
    public String employeeForm() { return "employee-form"; }
}


package com.lab10.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String customersPage() {
        return "index"; // ชื่อไฟล์ customers.html ใน templates
    }
}
package com.a14.emart.backendsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
class HomePageController {

    @GetMapping("/")
    public String homepage() {
        return "homePage";
    }
}
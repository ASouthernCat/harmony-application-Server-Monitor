package com.example.serverdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class helloController {
    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }
}

package com.example.springcrud2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class HomeController {
    @GetMapping("/")
    public ModelAndView home() { // -> homeController
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index"); //뷰의 이름
        return mv; // view + data passvariable
    }
}

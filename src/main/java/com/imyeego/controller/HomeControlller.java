package com.imyeego.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeControlller {

    @RequestMapping(value = "home")
    public String home(Model model){
        return "index";
    }
}

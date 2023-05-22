package com.sau.sbsecurity.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
    @GetMapping("/about")
    public String getAbout(){
        return "about/index";
    }
}
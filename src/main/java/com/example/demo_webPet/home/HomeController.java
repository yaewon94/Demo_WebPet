package com.example.demo_webPet.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        //return "home"; // 반환값이 .html
        return "redirect:/signup"; // redirect 는 GET 요청으로 이동함
    }
}

package com.example.demo_webPet.home;

import com.example.demo_webPet.Common.UrlConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("url_signup", UrlConstants.URL_SIGNUP);
        return "home"; // 반환값이 .html
    }
}

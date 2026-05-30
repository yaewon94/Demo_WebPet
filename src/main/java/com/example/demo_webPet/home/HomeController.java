package com.example.demo_webPet.home;

import com.example.demo_webPet.Common.UrlConstants;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model, HttpSession session){
        model.addAttribute("url_signup", UrlConstants.URL_SIGNUP);
        model.addAttribute("login_user_info", session.getAttribute("login_user_info"));
        return "home"; // 반환값이 .html
    }
}

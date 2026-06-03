package com.example.demo_webPet.home;

import com.example.demo_webPet.common.UrlConstants;
import com.example.demo_webPet.common.session.SessionManager;
import com.example.demo_webPet.common.session.SessionUserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model, HttpSession session){
        model.addAttribute("url_signup", UrlConstants.URL_SIGNUP);
        model.addAttribute("url_login", UrlConstants.URL_LOGIN);
        model.addAttribute(SessionUserDto.SESSION_KEY, SessionManager.getLoginUser(session));
        return "home"; // 반환값이 .html
    }
}

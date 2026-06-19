package com.example.demo_webPet.home;

import com.example.demo_webPet.auth.AuthService;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
final class HomeController {

    private final AuthService authService;

    @GetMapping("/")
    public String homePage(Model model, HttpSession session){

        User user = authService.getLoginUser(session);

        // 회원가입, 로그인, 로그아웃 패널
        if(user != null){
            model.addAttribute("has_login_user", true);
            model.addAttribute("user_name", user.getUserName());
            model.addAttribute("url_logout", UrlConstants.URL_LOGOUT);
        }else{
            model.addAttribute("url_signup", UrlConstants.URL_SIGNUP);
            model.addAttribute("url_login", UrlConstants.URL_LOGIN);
        }

        // 게시판 패널
        model.addAttribute("url_board_missing_animal_list", UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST);
        model.addAttribute("url_board_rescued_animal_list", UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST);

        return "home"; // 반환값이 .html
    }
}

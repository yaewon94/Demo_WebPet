package com.example.demo_webPet.home;

import com.example.demo_webPet.common.constants.UrlConstants;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
final class HomeController {

    @GetMapping("/")
    public String homePage(Model model, HttpSession session){

        // 게시판 패널
        model.addAttribute("url_board_missing_animal_list", UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST);
        model.addAttribute("url_board_rescued_animal_list", "/board/rescuedAnimal/list");

        return "home"; // 반환값이 .html
    }
}

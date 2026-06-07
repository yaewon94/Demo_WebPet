package com.example.demo_webPet.board;

import com.example.demo_webPet.common.constants.UrlConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
final class AnimalShelterNoticeBoardController {

    @GetMapping(UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_LIST)
    String showAnimalListPage(Model model){
        model.addAttribute("url_add_board", UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD);
        return UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_LIST;
    }

    @GetMapping(UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD)
    String showAnimalAddPage(Model model){
        // 로그인 여부, 보호소 계정 여부는 interceptor에서 처리
        return UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD;
    }
}

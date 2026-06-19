package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.view.ModelParamConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
final class MissingAnimalBoardController {

    @ModelAttribute("list_animalSpecies") // GET, POST 둘다 적용
    private AnimalSpecies[] animalSpecies() {
        return AnimalSpecies.values();
    }

    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST)
    public String showAnimalListPage(Model model){
        model.addAttribute(ModelParamConstants.URL, UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD);
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST;
    }

    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD)
    public String showAnimalAddPage(Model model){
        // 로그인 여부는 interceptor에서 처리
        // TODO : 일반계정만 쓸수 있게

        model.addAttribute("url", UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD);
        if(!model.containsAttribute("request")){
            model.addAttribute("request", MissingAnimalBoardWriteRequest.getNewInstance());
        }
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD;
    }
}

package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.auth.LoginUserDto;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.view.ModelParamConstants;
import com.example.demo_webPet.common.util.ValidationCheck;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
final class MissingAnimalBoardController {

    private final MissingAnimalBoardService boardService;

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
        // 로그인 여부, 일반계정 여부는 interceptor에서 처리

        model.addAttribute("url", UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD);
        if(!model.containsAttribute("request")){
            model.addAttribute("request", MissingAnimalBoardWriteRequest.getNewInstance());
        }
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD;
    }

    // BindingResult 는 반드시 검증 대상 뒤에 와야함
    @PostMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD)
    public String processAnimalAddPage(
            @Valid @ModelAttribute("request") MissingAnimalBoardWriteRequest request,
            BindingResult bindingResult,
            @ModelAttribute("loginUser") LoginUserDto loginUser,
            Model model,
            HttpSession session){

        // BindingResult 검증
        ObjectError error = ValidationCheck.getFirstError(bindingResult);
        if(error != null){
            model.addAttribute(ModelParamConstants.ERROR_MSG, error.getDefaultMessage());
            return UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD;
        }

        // service
        boardService.addBoard(request, loginUser.id());

        return "redirect:" + UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST;
    }
}

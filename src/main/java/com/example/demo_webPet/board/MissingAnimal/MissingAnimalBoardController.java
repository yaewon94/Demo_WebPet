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
import org.springframework.web.bind.annotation.*;

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
        Long board_id = boardService.addBoard(request, loginUser.id());

        // 자기가 쓴 게시물 페이지로 리다이렉트
        return "redirect:" + UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL + "?id=" + board_id;
    }

    // 스프링 URL 매핑시 common.css 같은것도 매핑 후보로 보기 때문에
    // 컨트롤러에 @RequestMapping(url)을 명시해주지 않으면 오류남
    //@GetMapping("/{id}")
    //public String detail(@PathVariable Long id, Model model)
    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL)
    public String detail(@RequestParam Long id, Model model){

        MissingAnimalBoard board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL;
    }
}

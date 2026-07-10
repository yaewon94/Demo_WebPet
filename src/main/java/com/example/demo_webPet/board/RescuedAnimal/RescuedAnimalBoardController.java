package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.animal.AnimalGender;
import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.board.BoardConstants;
import com.example.demo_webPet.board.BoardController;
import com.example.demo_webPet.board.BoardDto_forList;
import com.example.demo_webPet.board.PagingResponse;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.util.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
final class RescuedAnimalBoardController extends BoardController {

    private final RescuedAnimalBoardService boardService;

    @ModelAttribute("list_animalSpecies") // GET, POST 둘다 적용
    private AnimalSpecies[] animalSpecies() {
        return AnimalSpecies.values();
    }
    @ModelAttribute("list_animalGender")
    private AnimalGender[] animalGenders() { return AnimalGender.values(); }

    @GetMapping(UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST)
    public String showAnimalListPage(
            @RequestParam(defaultValue = BoardConstants.DEFAULT_PAGE) int page,
            Model model){
        String urlPrefix = UriBuilder.getUrl(
                UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST,
                Map.of("page", ""));
        Page<BoardDto_forList> boardList = boardService.getBoardList(page);
        model.addAttribute(BoardConstants.MODEL_PARAM_BOARD_LIST, boardList);
        model.addAttribute(
                BoardConstants.MODEL_PARAM_PAGING,
                new PagingResponse(urlPrefix, page, boardList.getTotalPages()));
        return UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST;
    }

    @GetMapping(UrlConstants.URL_BOARD_RESCUED_ANIMAL_ADD)
    public String showAnimalAddPage(Model model){
        // 로그인 여부, 보호소 계정 여부는 interceptor에서 처리

        model.addAttribute("url", UrlConstants.URL_BOARD_RESCUED_ANIMAL_ADD);
        if(!model.containsAttribute("request")){
            model.addAttribute("request", RescuedAnimalBoardWriteRequest.getNewInstance());
        }
        return UrlConstants.URL_BOARD_RESCUED_ANIMAL_ADD;
    }

    /*@PostMapping(UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD)
    public String processAnimalAddBoard(@Valid @ModelAttribute("request") RescuedAnimalBoardWriteRequest request,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpSession session){
        // java validation 검증
        if(bindingResult.hasErrors()){
            model.addAttribute(ModelParamConstants.ERROR_MSG, bindingResult.getFieldErrors().getFirst().getDefaultMessage());
            return UrlConstants.URL_BOARD_ANIMAL_SHELTER_NOTICE_ADD;
        }

        // service
        service.create(request, SessionManager.getLoginUser(session).id());

        // TODO : 내가 쓴 글 페이지로 이동
    }*/

    @Override
    protected String getAddUrl(){ return UrlConstants.URL_BOARD_RESCUED_ANIMAL_ADD; }

    @Override
    protected String getListUrl() {
        return UrlConstants.URL_BOARD_RESCUED_ANIMAL_LIST;
    }

    @Override
    protected String getDetailUrl() {
        return UrlConstants.URL_BOARD_RESCUED_ANIMAL_DETAIL;
    }

    @Override
    protected String getModifyUrl() {
        return UrlConstants.URL_BOARD_RESCUED_ANIMAL_MODIFY;
    }

    @Override
    protected String getDeleteUrl() {
        return UrlConstants.URL_BOARD_RESCUED_ANIMAL_DELETE;
    }
}

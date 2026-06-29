package com.example.demo_webPet.board.MissingAnimal;

import com.example.demo_webPet.animal.AnimalSpecies;
import com.example.demo_webPet.auth.LoginUserDto;
import com.example.demo_webPet.board.*;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.ModelParamConstants;
import com.example.demo_webPet.common.util.UriBuilder;
import com.example.demo_webPet.common.util.ValidationCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequiredArgsConstructor
final class MissingAnimalBoardController extends BoardController {

    private static final String VIEW_NAME_WRITE = "/board/missingAnimal/write";
    private final MissingAnimalBoardService boardService;
    private final BoardCommentService commentService;

    @ModelAttribute("list_animalSpecies") // GET, POST 둘다 적용
    private AnimalSpecies[] animalSpecies() {
        return AnimalSpecies.values();
    }

    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST)
    public String showAnimalListPage(
            @RequestParam(defaultValue = BoardConstants.DEFAULT_PAGE) int page,
            Model model){
        String urlPrefix = UriBuilder.getUrl(
                UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST,
                Map.of("page", ""));
        Page<BoardDto_forList> boardList = boardService.getBoardList(page);
        model.addAttribute(BoardConstants.MODEL_PARAM_BOARD_LIST, boardList);
        model.addAttribute(
                BoardConstants.MODEL_PARAM_PAGING,
                new PagingResponse(urlPrefix, page, boardList.getTotalPages()));

        return UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST;
    }

    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD)
    public String showAnimalAddPage(Model model){
        // 로그인 여부, 일반계정 여부는 interceptor에서 처리

        // 기존 form에 작성한 값들 유지할 수 있도록
        if(!model.containsAttribute("request")){
            model.addAttribute("request", MissingAnimalBoardWriteRequest.getNewInstance());
        }

        return VIEW_NAME_WRITE;
    }

    // BindingResult 는 반드시 검증 대상 뒤에 와야함
    @PostMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD)
    public String processBoardAdd(
            @Valid @ModelAttribute("request") MissingAnimalBoardWriteRequest request,
            BindingResult bindingResult,
            @ModelAttribute("loginUser") LoginUserDto loginUser,
            Model model){
        // BindingResult 검증
        ObjectError error = ValidationCheck.getFirstError(bindingResult);
        if(error != null){
            model.addAttribute(ModelParamConstants.ERROR_MSG, error.getDefaultMessage());
            return VIEW_NAME_WRITE;
        }

        // service
        Long board_id = boardService.addBoard(request, loginUser.id());

        // 자기가 쓴 게시물 페이지로 리다이렉트
        return "redirect:"
                + UriBuilder.getUrl(
                        UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL,
                        Map.of("id", String.valueOf(board_id)));
    }

    // 스프링 URL 매핑시 common.css 같은것도 매핑 후보로 보기 때문에
    // 컨트롤러에 @RequestMapping(url)을 명시해주지 않으면 오류남
    //@GetMapping("/{id}")
    //public String detail(@PathVariable Long id, Model model)
    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL)
    public String detail(@RequestParam Long id,
                         @RequestParam(defaultValue = BoardConstants.DEFAULT_PAGE) int page,
                         Model model){
        Page<BoardCommentResponse> commentList = commentService.getCommentList(BoardType.MISSING_ANIMAL, id, page);
        String urlPrefix = UriBuilder.getUrl(
                UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL,
                Map.of("id", id.toString(), "commentPage", ""));

        model.addAttribute("board", boardService.getBoard(id));
        model.addAttribute(BoardConstants.MODEL_PARAM_BOARD_COMMENT_LIST, commentList);
        model.addAttribute(
                BoardConstants.MODEL_PARAM_PAGING,
                new PagingResponse(urlPrefix, page, commentList.getTotalPages()));

        return UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL;
    }

    @GetMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_MODIFY)
    public String showAnimalModifyPage(
            @RequestParam Long id,
            @ModelAttribute("loginUser") LoginUserDto loginUser,
            Model model){
        model.addAttribute("request", boardService.getBoard(id, loginUser.id()));
        return VIEW_NAME_WRITE;
    }

    @PostMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_MODIFY)
    public String processBoardModify(
            @Valid @ModelAttribute("request") MissingAnimalBoardWriteRequest request,
            BindingResult bindingResult,
            @ModelAttribute("loginUser") LoginUserDto loginUser,
            Model model){
        // BindingResult 검증
        ObjectError error = ValidationCheck.getFirstError(bindingResult);
        if(error != null){
            model.addAttribute(ModelParamConstants.ERROR_MSG, error.getDefaultMessage());
            return VIEW_NAME_WRITE;
        }

        // service
        boardService.modifyBoard(request, loginUser.id());

        // 수정한 게시물 페이지로 리다이렉트
        return "redirect:"
                + UriBuilder.getUrl(
                UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL,
                Map.of("id", String.valueOf(request.id())));
    }

    @PostMapping(UrlConstants.URL_BOARD_MISSING_ANIMAL_DELETE)
    public String deleteBoard(
            @RequestParam Long id,
            @ModelAttribute("loginUser") LoginUserDto loginUser){
        boardService.deleteBoard(id, loginUser.id());
        // 게시물 목록으로 이동
        return "redirect:" + UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST;
    }

    @Override
    protected String getAddUrl(){ return UrlConstants.URL_BOARD_MISSING_ANIMAL_ADD; }

    @Override
    protected String getListUrl() {
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_LIST;
    }

    @Override
    protected String getDetailUrl() {
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_DETAIL;
    }

    @Override
    protected String getModifyUrl() {
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_MODIFY;
    }

    @Override
    protected String getDeleteUrl() {
        return UrlConstants.URL_BOARD_MISSING_ANIMAL_DELETE;
    }
}

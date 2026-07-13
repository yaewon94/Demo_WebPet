package com.example.demo_webPet.board.RescuedAnimal;

import com.example.demo_webPet.board.*;
import com.example.demo_webPet.common.util.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/rescuedAnimal")
final class RescuedAnimalBoardController extends BoardController {

    private final RescuedAnimalBoardService boardService;

    @GetMapping("/list")
    public String showAnimalListPage(
            @RequestParam(defaultValue = BoardConstants.DEFAULT_PAGE) int page,
            @RequestParam(required = false) String sidoCode,
            @RequestParam(required = false) String sigunguCode,
            Model model){

        Page<BoardDto_forList> boardList;

        if(sidoCode == null || sidoCode.isBlank()){
            boardList = boardService.getBoardList(page);
        }else{
            boardList = boardService.getBoardList(
                    page,
                    Objects.requireNonNullElse(sigunguCode, sidoCode));
        }

        model.addAttribute(BoardConstants.MODEL_PARAM_BOARD_LIST, boardList);
        model.addAttribute(
                BoardConstants.MODEL_PARAM_PAGING,
                new PagingResponse(
                        UriBuilder.getUrl("/board/rescuedAnimal/list", Map.of("page", "")),
                        page,
                        boardList.getTotalPages()));
        return "/board/rescuedAnimal/list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model){
        String urlPrefix = UriBuilder.getUrl(
                "/board/rescuedAnimal/detail",
                Map.of("id", id.toString()));

        model.addAttribute("board", boardService.getBoard(id));
        return "/board/rescuedAnimal/detail";
    }

    @Override
    protected String getAddUrl(){ return null; }

    @Override
    protected String getListUrl() {
        return "/board/rescuedAnimal/list";
    }

    @Override
    protected String getDetailUrl() {
        return "/board/rescuedAnimal/detail";
    }

    @Override
    protected String getModifyUrl() {
        return null;
    }

    @Override
    protected String getDeleteUrl() {
        return null;
    }
}

package com.example.demo_webPet.board;

import com.example.demo_webPet.common.output.ModelParamConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class BoardExceptionHandler {

    @ExceptionHandler(BoardDeniedException.class)
    public String handle(BoardDeniedException e, RedirectAttributes ra) {
        // TODO : 리다이렉션 메소드 만들어서 분리
        String redirectUrl = "";
        String prevUrl = e.getPrevUrl();

        ra.addFlashAttribute(ModelParamConstants.ALERT, e.getMessage());
        if(prevUrl != null && prevUrl.isBlank()) redirectUrl = e.getPrevUrl();
        return "redirect:/" + redirectUrl;
    }
}

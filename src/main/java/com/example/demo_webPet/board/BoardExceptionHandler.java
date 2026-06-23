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
        ra.addFlashAttribute(ModelParamConstants.ALERT, e.getMessage());
        return "redirect:/";
    }
}

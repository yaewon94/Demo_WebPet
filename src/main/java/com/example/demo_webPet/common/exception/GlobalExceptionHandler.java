package com.example.demo_webPet.common.exception;

import com.example.demo_webPet.user.UserAuthException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// try블록이 있으면 @ControllerAdvice보다 먼저 호출됨
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAuthException.class)
    public String handle(UserAuthException e, RedirectAttributes ra) {
        ra.addFlashAttribute("errorMsg", e.getMessage());
        return "redirect:" + e.getRedirectionPage();
    }

    /*@ExceptionHandler(UserIdDuplicatedException.class)
    public String handle(UserIdDuplicatedException e
            , RedirectAttributes ra
            , HttpServletRequest request){
        ra.addFlashAttribute("errorMsg", e.getMessage());
        return "redirect:/user";
    }*/

    /*public ResponseEntity<Response> handle(UserIdDuplicatedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new Response(e.getCode().getCode(), e.getMessage()));
    }*/
}

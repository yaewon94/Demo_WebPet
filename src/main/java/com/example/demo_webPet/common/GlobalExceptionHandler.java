package com.example.demo_webPet.common;

import org.springframework.web.bind.annotation.ControllerAdvice;

//@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

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

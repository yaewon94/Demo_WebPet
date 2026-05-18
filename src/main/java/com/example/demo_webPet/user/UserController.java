package com.example.demo_webPet.user;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private static final String MODEL_NAME = "userSignupDto";

    @GetMapping("/signup")
    public String signupPage(Model model){
        //model.addAttribute(MODEL_NAME, new UserSignupDto()); // dto class
        model.addAttribute(MODEL_NAME, UserSignupDto.getNewInstance()); // record
        return "signup"; // html 파일명
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute(MODEL_NAME) UserSignupDto dto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            //System.out.println("아이디 = " + dto.getId());
            System.out.println("아이디 = " + dto.id() );
            return "signup";
        }

        return "redirect:/";
    }
}
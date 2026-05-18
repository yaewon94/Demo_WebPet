package com.example.demo_webPet.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor // final 필드 + @NonNull 필드만 골라서 생성자를 자동으로 만들어주는 것
public class UserController {

    private final UserService userService;

    // TEMP
    @GetMapping("/user")
    public String signupPage(Model model){
        //model.addAttribute(MODEL_NAME, new UserSignupDto()); // dto class
        model.addAttribute("createUserDto", CreateUserDto.getNewInstance()); // record
        return "signup"; // html 파일명
    }

    @PostMapping("/user")
    public String createUser(@Valid @ModelAttribute("createUserDto") CreateUserDto dto, BindingResult bindingResult, Model model) {

        // java validation 레벨에서 입력값 검증
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMsg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return "signup";
        }

        // service 레벨에서 입력값 검증
        try {
            userService.signup(dto);
        } catch (UserIdDuplicatedException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }
}
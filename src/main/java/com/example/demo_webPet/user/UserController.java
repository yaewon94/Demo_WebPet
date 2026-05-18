package com.example.demo_webPet.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
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

        // 1. java validation 레벨에서 입력값 검증
        String errorMsg = null;
        // 바인딩 오류가 여러개일 경우 아이디 오류 출력을 우선순위로 하기 위해
        for (String field : Arrays.asList("userId", "password")) {

            FieldError error = bindingResult.getFieldError(field);

            if (error != null) {
                errorMsg = error.getDefaultMessage();
                break;
            }
        }

        if (errorMsg != null) {
            model.addAttribute("errorMsg", errorMsg);
            return "signup";
        }

        // 2. service 레벨에서 입력값 검증
        try {
            userService.createUser(dto);
        } catch (UserIdDuplicatedException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }
}
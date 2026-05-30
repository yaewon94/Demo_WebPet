package com.example.demo_webPet.user;

import com.example.demo_webPet.Common.UrlConstants;
import com.example.demo_webPet.user.CreateUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor // final 필드 + @NonNull 필드만 골라서 생성자를 자동으로 만들어주는 것
class UserController {

    private static final String VIEW_NAME_SIGN_UP = "signup";

    private final UserService userService;

    // TEMP
    @GetMapping(UrlConstants.URL_SIGNUP)
    public String signupPage(Model model){
        model.addAttribute("url", UrlConstants.URL_SIGNUP);
        if (!model.containsAttribute("createUserRequest")) {
            model.addAttribute("createUserRequest", CreateUserRequest.getNewInstance()); // record
        }
        return VIEW_NAME_SIGN_UP; // html 파일명
    }

    @PostMapping(UrlConstants.URL_SIGNUP)
    public String createUser(@Valid @ModelAttribute("createUserRequest") CreateUserRequest request
            , BindingResult bindingResult
            , Model model
            , RedirectAttributes ra) {

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
            return VIEW_NAME_SIGN_UP;
        }

        // 2. service 레벨에서 입력값 검증
        // TODO :
        // - REST로 바꿀때 RestControllerAdvice에서 에러(메세지 등) 정보 반환
        // - 기존 form 데이터 유지, 페이지 이동은 프론트가 하도록 변경
        try {
            userService.createUser(request);
        }catch(UserIdDuplicatedException e){
            ra.addFlashAttribute("errorMsg", e.getMessage());
            ra.addFlashAttribute("createUserRequest", request);
            return "redirect:" + UrlConstants.URL_SIGNUP;
        }

        // 회원가입 성공 시
        // TODO : 프론트에서 회원가입 성공 메세지 출력 후, 홈화면으로 이동
        return "redirect:/"; // redirect는 Get메소드 호출
    }
}
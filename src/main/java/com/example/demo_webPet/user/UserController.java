package com.example.demo_webPet.user;

import com.example.demo_webPet.auth.AuthService;
import com.example.demo_webPet.common.output.ModelParamConstants;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.util.ValidationCheck;
import com.example.demo_webPet.shelter.ShelterDto;
import com.example.demo_webPet.shelter.ShelterService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor // final 필드 + @NonNull 필드만 골라서 생성자를 자동으로 만들어주는 것
final class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final ShelterService shelterService;

    @ModelAttribute("list_userType") // GET, POST 둘다 적용
    private UserType[] userTypes() {
        return UserType.values();
    }
    @ModelAttribute("list_shelter")
    private List<ShelterDto> shelters() { return shelterService.getShelterList(); }

    // TEMP
    @GetMapping(UrlConstants.URL_SIGNUP)
    public String signupPage(Model model){
        model.addAttribute("url", UrlConstants.URL_SIGNUP);

        if (!model.containsAttribute("request")) {
            model.addAttribute("request", CreateUserRequest.getNewInstance()); // record
        }
        return UrlConstants.URL_SIGNUP; // html 파일명
    }

    @PostMapping(UrlConstants.URL_SIGNUP)
    public String createUser(@Valid @ModelAttribute("request") CreateUserRequest request
            , BindingResult bindingResult
            , Model model) throws IllegalAccessException {

        // 1. java validation 레벨에서 입력값 검증
        String errorMsg = null;
        FieldError fieldError = null;

        if(request.user_type() == UserType.SHELTER){
            fieldError = ValidationCheck.getFirstFieldError(bindingResult
                    , "user_name", "password", "user_type", "shelter_id");
        }else{
            fieldError = ValidationCheck.getFirstFieldError(bindingResult
                    , "user_name", "password", "user_type");
        }

        if(fieldError != null){
            errorMsg = fieldError.getDefaultMessage();
            if(fieldError.getField().equals("shelter_id")){
                model.addAttribute("errorCode", "SHELTER_REQUIRED");
            }
        }else{
            ObjectError globalError = bindingResult.getGlobalError();
            if(globalError != null) errorMsg = globalError.getDefaultMessage();
        }

        if (errorMsg != null) {
            model.addAttribute(ModelParamConstants.ERROR_MSG, errorMsg);
            model.addAttribute("request", request);
            return UrlConstants.URL_SIGNUP;
        }

        // 2. service 레벨에서 입력값 검증
        // TODO :
        // - REST로 바꿀때 RestControllerAdvice에서 에러(메세지 등) 정보 반환
        // - 기존 form 데이터 유지, 페이지 이동은 프론트가 하도록 변경
        User user = userService.createUser(request);

        // 회원가입 성공 시, 로그인 정보 저장
        authService.autoLogin(user);

        // TODO : 프론트에서 회원가입 성공 메세지 출력
        return "redirect:/"; // redirect는 Get메소드 호출
    }
}
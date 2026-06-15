package com.example.demo_webPet.user;

import com.example.demo_webPet.common.constants.ModelParamConstants;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.session.SessionManager;
import com.example.demo_webPet.common.util.ErrorCheck;
import com.example.demo_webPet.shelter.ShelterDto;
import com.example.demo_webPet.shelter.ShelterService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor // final 필드 + @NonNull 필드만 골라서 생성자를 자동으로 만들어주는 것
class UserController {

    private final UserService userService;
    private final ShelterService shelterService;

    @ModelAttribute("list_userTypes") // GET, POST 둘다 적용
    UserType[] userTypes() {
        return UserType.values();
    }
    @ModelAttribute("list_shelter")
    public List<ShelterDto> shelters() { return shelterService.getShelterList(); }

    // TEMP
    @GetMapping(UrlConstants.URL_SIGNUP)
    String signupPage(Model model){
        model.addAttribute("url", UrlConstants.URL_SIGNUP);

        if (!model.containsAttribute("request")) {
            model.addAttribute("request", CreateUserRequest.getNewInstance()); // record
        }
        return UrlConstants.URL_SIGNUP; // html 파일명
    }

    @PostMapping(UrlConstants.URL_SIGNUP)
    String createUser(@Valid @ModelAttribute("request") CreateUserRequest request
            , BindingResult bindingResult
            , Model model
            , RedirectAttributes ra
            , HttpSession session) {

        // 1. java validation 레벨에서 입력값 검증
        String errorMsg = ErrorCheck.validationCheckInOrder(bindingResult
                , "userId", "password", "type", "shelter_id");
        if (errorMsg != null) {
            model.addAttribute(ModelParamConstants.ERROR_MSG, errorMsg);
            model.addAttribute("request", request);
            return UrlConstants.URL_SIGNUP;
        }

        // 2. service 레벨에서 입력값 검증
        // TODO :
        // - REST로 바꿀때 RestControllerAdvice에서 에러(메세지 등) 정보 반환
        // - 기존 form 데이터 유지, 페이지 이동은 프론트가 하도록 변경
        LoginUserDto loginUserDto;

        try {
            loginUserDto = userService.createUser(request);
        }catch(UserException e){
            ra.addFlashAttribute(ModelParamConstants.ERROR_MSG, e.getCode().getMessage());
            ra.addFlashAttribute("request", request);
            return "redirect:" + UrlConstants.URL_SIGNUP;
        }

        // 회원가입 성공 시
        // 세션에 로그인 정보 저장
        SessionManager.login(loginUserDto, session);

        // TODO : 프론트에서 회원가입 성공 메세지 출력
        return "redirect:/"; // redirect는 Get메소드 호출
    }

    @GetMapping(UrlConstants.URL_LOGIN)
    String loginPage(@RequestParam(required = false)String redirect, Model model){
        model.addAttribute("url", UrlConstants.URL_LOGIN);
        model.addAttribute("loginUserDto", LoginUserDto.getNewInstance());
        model.addAttribute("redirect", redirect);
        return UrlConstants.URL_LOGIN;
    }

    @PostMapping(UrlConstants.URL_LOGIN)
    String loginUser(@RequestParam(required = false)String redirect
            , @Valid @ModelAttribute("loginUserDto") LoginUserDto loginUserDto
            , BindingResult bindingResult
            , Model model
            , HttpSession session){

        // 1. validation 검증
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                model.addAttribute("errorMsg", fieldError.getDefaultMessage());
            }
            return UrlConstants.URL_LOGIN;
        }

        // 2. service 검증
        // 예외 발생할 경우 @ControllerAdvice에서 처리
        userService.loginUser(loginUserDto);

        // 로그인 정보 저장
        SessionManager.login(loginUserDto, session);

        // 페이지 이동
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        return "redirect:/";
    }

    @PostMapping(UrlConstants.URL_LOGOUT)
    String logout(HttpSession session){
        SessionManager.logout(session);
        return "redirect:/";
    }
}
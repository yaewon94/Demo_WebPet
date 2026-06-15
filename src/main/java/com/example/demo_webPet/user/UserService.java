package com.example.demo_webPet.user;
import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.shelter.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserService {

    private final UserRepository userRepository;
    private final ShelterService shelterService;

    @Transactional
    LoginUserDto createUser(CreateUserRequest dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserId(dto.userId())){
            throw new UserException(UserCode.ERROR_DUPLICATED_USER_ID);
        }

        User user = new User(dto.type());
        user.setUserId(dto.userId());
        user.setPassword(dto.password());
        user.setShelter(shelterService.getShelter(dto.shelter_id()));
        userRepository.save(user);

        // TODO : user id(PK) 만 넣기
        return new LoginUserDto(user.getId(), user.getUserId(), null, user.getType());
    }

    void loginUser(LoginUserDto dto){
        // 아이디 존재 여부 체크
        User user = userRepository
                .findByUserId(dto.userId())
                .orElseThrow(() ->
                        new UserAuthException(UserCode.ERROR_USER_ID_IS_NOT_EXIST, UrlConstants.URL_LOGIN));

        // 비밀번호 일치 여부 체크
        if (!user.getPassword().equals(dto.password())) {
            throw new UserAuthException(UserCode.ERROR_USER_PASSWORD_MISMATCH, UrlConstants.URL_LOGIN);
        }
    }
}
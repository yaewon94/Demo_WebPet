package com.example.demo_webPet.user;
import com.example.demo_webPet.common.exception.ErrorCode;
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
    User createUser(CreateUserRequest dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserName(dto.user_name())){
            throw new UserException(ErrorCode.ERROR_DUPLICATED_USER_NAME, dto);
        }

        User user = new User(dto.user_type());
        user.setUserName(dto.user_name());
        user.setPassword(dto.password());
        user.setShelter(shelterService.getShelter(dto.shelter_id()));
        userRepository.save(user);

        return user;
    }
}
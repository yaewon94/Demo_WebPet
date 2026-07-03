package com.example.demo_webPet.user;
import com.example.demo_webPet.common.error.ErrorCode;
import com.example.demo_webPet.shelter.ShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShelterService shelterService;

    @Transactional
    User createUser(CreateUserRequest dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserName(dto.username())){
            throw new UserException(ErrorCode.ERROR_DUPLICATED_USER_NAME, dto);
        }

        User user = new User(dto.user_type());
        user.setUserName(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setShelter(shelterService.getShelter(dto.shelter_id()));
        userRepository.save(user);

        return user;
    }
}
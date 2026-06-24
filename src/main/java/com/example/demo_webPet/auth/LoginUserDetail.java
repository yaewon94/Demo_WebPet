package com.example.demo_webPet.auth;

import com.example.demo_webPet.user.User;
import com.example.demo_webPet.user.UserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class LoginUserDetail implements UserDetails {

   @Getter
    private final LoginUserDto dto; // 외부 노출용
    private final String password;

    public LoginUserDetail(User user) {
        this.dto = LoginUserDto.from(user);
        this.password = user.getPassword();
    }

    @Override
    public String getUsername() {
        return dto.user_name();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public UserType getUsertype() {
        return dto.user_type();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}

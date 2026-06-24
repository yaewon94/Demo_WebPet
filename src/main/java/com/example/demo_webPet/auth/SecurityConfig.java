package com.example.demo_webPet.auth;

import com.example.demo_webPet.common.constants.UrlConstants;
import com.example.demo_webPet.common.output.ModelParamConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .securityContext(sc -> sc.requireExplicitSave(false))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                UrlConstants.URL_SIGNUP,
                                UrlConstants.URL_LOGIN,
                                "/css/**",
                                "/js/**")
                        .permitAll()
                        .requestMatchers(
                                "/**/add",
                                "/**/modify",
                                "/**/delete",
                                "/logout"
                        ).authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(UrlConstants.URL_LOGIN)
                        .loginProcessingUrl(UrlConstants.URL_LOGIN) // @PostMapping 을 부르지 않고 얘가 가로챔
                        .defaultSuccessUrl("/", false) // saved request가 있어도 default url을 사용할거냐 => false
                        .permitAll()
                        .failureHandler((req, res, ex) -> {

                            String msg = switch (ex) {
                                case BadCredentialsException badCredentialsException -> "아이디 또는 비밀번호가 틀렸습니다.";
                                case UsernameNotFoundException usernameNotFoundException -> "존재하지 않는 사용자입니다.";
                                default -> "로그인 실패";
                            };


                            req.getSession().setAttribute(ModelParamConstants.ERROR_MSG, msg);
                            res.sendRedirect("/login");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl(UrlConstants.URL_LOGOUT)
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                // csrf
        http    .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // security context
                .securityContext(sc -> sc.requireExplicitSave(false))
                // url 권한설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                UrlConstants.URL_SIGNUP,
                                UrlConstants.URL_LOGIN,
                                "/board/comment/**",
                                "/upload/**",
                                "/**/list",
                                "/**/detail",
                                "/css/**",
                                "/js/**",
                                "/address/**",
                                "/error")
                        .permitAll()
                        .requestMatchers(
                                "/**/add",
                                "/**/modify",
                                "/**/delete",
                                "/logout"
                        ).authenticated()
                        .anyRequest().authenticated()
                )
                // form login
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
                )
                // ajax 예외처리
                .exceptionHandling(exception -> {

                    // 로그인 안 된 경우 (401)
                    exception.authenticationEntryPoint((req, res, ex) -> {

                        if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                            res.setStatus(401);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("""
                        {"type":"UNAUTHORIZED","message":"로그인이 필요합니다"}
                    """);
                            return;
                        }

                        res.sendRedirect(UrlConstants.URL_LOGIN);
                    });

                    // 권한 없음 (403)
                    exception.accessDeniedHandler((req, res, ex) -> {

                        if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                            res.setStatus(403);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("""
                        {"type":"FORBIDDEN","message":"권한이 없습니다"}
                    """);
                            return;
                        }

                        res.sendRedirect("/error/403");
                    });
                });

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
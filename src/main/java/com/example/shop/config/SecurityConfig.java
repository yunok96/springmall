package com.example.shop.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable());
//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
//                .ignoringRequestMatchers("/login")
//        );

        // 권한 설정
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/write", "/edit/**", "/delete/**").authenticated() // "/write" 경로는 인증된 사용자만 접근 가능
                .anyRequest().permitAll() // 그 외 경로는 모두 허용
        );

        // 로그인 설정
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/")
        );

        // 로그아웃 설정
//        http.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
        http.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler()));

        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) -> {
            String referrer = request.getHeader("Referer");
            if (referrer != null) {
                response.sendRedirect(referrer); // 이전 페이지로 리다이렉트
            } else {
                response.sendRedirect("/"); // 리퍼러가 없으면 기본 경로로 이동
            }
        };
    }

}

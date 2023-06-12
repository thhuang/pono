package dev.thhuang.saide.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandle;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(conf ->
                conf.anyRequest().authenticated()
        ).oauth2Login(conf ->
                conf.successHandler(successHandler).failureHandler(failureHandle)
        );
        return http.build();
    }
}

@Component
class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // TODO: get the targetUrl from env or const file
    private final static String targetUrl = "http://localhost:5173/saide";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request);
    }
}

@Component
class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    // TODO: get the targetUrl from env or const file
    private final static String targetUrl = "http://localhost:5173/saide/error";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

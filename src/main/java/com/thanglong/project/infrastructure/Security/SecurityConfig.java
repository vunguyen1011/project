package com.thanglong.project.infrastructure.Security;

import com.thanglong.project.infrastructure.Config.CustomAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy; // THÊM IMPORT NÀY
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    // MỞ RỘNG WHITE_LIST ĐỂ CHO PHÉP OAUTH2 VÀ SWAGGER
    private final String[] WHITE_LIST = {
            "/success",
            "/auths/**",
            "/oauth2/**",
            "/login/oauth2/code/**",
            "/login/**",
            "/swagger-ui/**"
    };

    private final JwtFilter jwtFilter;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(WHITE_LIST).permitAll()
                                .anyRequest().authenticated()
//                        request.anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customOAuth2SuccessHandler)
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(new CustomAuthEntryPoint()));

        return httpSecurity.build();
    }
}
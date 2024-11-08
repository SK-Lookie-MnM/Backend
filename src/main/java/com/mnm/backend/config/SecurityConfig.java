package com.mnm.backend.config;

<<<<<<< Updated upstream
=======

import com.mnm.backend.auth.filter.JwtFilter;
import com.mnm.backend.auth.jwt.JwtAccessDeniedHandler;
import com.mnm.backend.auth.jwt.JwtAuthenticationEntryPoint;
import com.mnm.backend.auth.util.TokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.context.annotation.Bean;

<<<<<<< Updated upstream
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .antMatchers("/api/qna/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .oauth2ResourceServer().jwt(); // JWT 인증 방식 적용
//        return http.build();
//    }
//}
=======
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(tokenProvider);
        http
                .cors(Customizer.withDefaults()) // CORS 설정 추가
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/users/login","api/users/reissue","/api/users/signup", "/api/users/checkEmail", "/api/users/checkLoginId", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // JwtSecurityConfig 적용

        ;// HTTP Basic 인증 비활성화
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080")); // 허용할 도메인 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
>>>>>>> Stashed changes

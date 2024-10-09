package com.mnm.backend.config;

import org.springframework.context.annotation.Bean;

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
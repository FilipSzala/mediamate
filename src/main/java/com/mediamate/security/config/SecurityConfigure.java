package com.mediamate.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigure {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests((autz ->autz
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()))
                .csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }
}
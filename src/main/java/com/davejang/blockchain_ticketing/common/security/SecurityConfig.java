package com.davejang.blockchain_ticketing.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/user/login"))
                        .not().authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/user/logout"))
                        .authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers((headers) -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/user/login?error=ture"))
                .logout((logout) -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login"))
                .sessionManagement((sessionManagement) -> sessionManagement
                        .invalidSessionUrl("/user/login")  // 세션 만료 시 리디렉션할 URL
                        .maximumSessions(1)  // 동시에 하나의 세션만 허용
                        .expiredUrl("/user/login"))
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }

    @Bean // 로그인 테스트용
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}

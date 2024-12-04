package com.davejang.blockchain_ticketing.common.security;

import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.LoginException;
import java.util.Collections;

//@RequiredArgsConstructor
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final MemberService memberService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String name = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        Member userInfo = memberService.loginUser(name);
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//
//}

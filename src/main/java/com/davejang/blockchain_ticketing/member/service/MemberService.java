package com.davejang.blockchain_ticketing.member.service;

import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.domain.Role;
import com.davejang.blockchain_ticketing.member.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @Transactional
    public Member registerUser(String username, String password) {
        Optional<Member> validUser = memberRepository.findByName(username);
        if(validUser.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 회원입니다");
        }

        Member member = Member.builder()
                .name(username)
                .password(password)
                .role(Role.USER)
                .build();

        return memberRepository.save(member);
    }

//    public Member loginUser(String username) {
//
//        Member loginMember = memberRepository.findByName(username).get();
//
//        return loginMember;
//    }


}

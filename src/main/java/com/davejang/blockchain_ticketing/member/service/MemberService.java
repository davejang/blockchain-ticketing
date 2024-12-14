package com.davejang.blockchain_ticketing.member.service;

import com.davejang.blockchain_ticketing.common.config.KaiaConfig;
import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.domain.Role;
import com.davejang.blockchain_ticketing.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Account;

import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    private KaiaConfig kaiaConfig;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @Transactional
    public Member registerUser(String username, String password) {
        String kaiaAddress;
        Optional<Member> validUser = memberRepository.findByName(username);

        if (validUser.isPresent()) {
            log.info("해당 유저가 이미 존재합니다. {}", username);
            throw new IllegalArgumentException("이미 등록된 회원입니다");
        }

        try {
            CaverExtKAS caver = kaiaConfig.initKas();
            Account account = caver.kas.wallet.createAccount();
            kaiaAddress = account.getAddress();
        } catch (Exception e) {
            throw new RuntimeException("KAS 연결 실패");
        }

        Member member = Member.builder()
                .name(username)
                .password(password)
                .kaiaAddress(kaiaAddress)
                .role(Role.USER)
                .build();

        return memberRepository.save(member);
    }


}

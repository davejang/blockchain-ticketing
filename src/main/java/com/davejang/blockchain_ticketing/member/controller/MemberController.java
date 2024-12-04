package com.davejang.blockchain_ticketing.member.controller;

import com.davejang.blockchain_ticketing.common.utils.ClientUtils;
import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.dto.MemberFormDto;
import com.davejang.blockchain_ticketing.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class MemberController {

    private final MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login(Model model,
                        HttpServletRequest request) {

        String currentIp = ClientUtils.getClientIp(request);
        model.addAttribute("currentIp", currentIp);
        return "loginForm";
    }

    @GetMapping("/register")
    public String memberRegisterGet(Model model) {
        model.addAttribute("memberForm", new MemberFormDto());
        return "registerForm";
    }

    @PostMapping("/register")
    public String memberRegisterPost(Model model,
                                 @ModelAttribute MemberFormDto memberForm,
                                 HttpSession session) {

        System.out.println("memberForm = " + memberForm.getName());
        Member registerMember = memberService
                .registerUser(memberForm.getName(), passwordEncoder.encode(memberForm.getPassword()));

        return "loginForm";
    }
}

package com.davejang.blockchain_ticketing.member.controller;

import com.davejang.blockchain_ticketing.common.utils.ClientUtils;
import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class MemberController {

    private final MemberService memberService;

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

    @PostMapping("/register")
    public String userRegister(Model model,
                               @Valid @ModelAttribute Member member,
                               HttpSession session) {

        Member registerMember = memberService.registerUser(member);
        model.addAttribute("username", member.getName());
        return "loginSuccess";
    }
}

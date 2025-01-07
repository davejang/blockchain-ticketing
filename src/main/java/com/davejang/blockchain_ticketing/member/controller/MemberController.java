package com.davejang.blockchain_ticketing.member.controller;

import com.davejang.blockchain_ticketing.common.config.KaiaConfig;
import com.davejang.blockchain_ticketing.common.utils.ClientUtils;
import com.davejang.blockchain_ticketing.member.domain.Member;
import com.davejang.blockchain_ticketing.member.dto.MemberFormDto;
import com.davejang.blockchain_ticketing.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/user")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private KaiaConfig kaiaConfig;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login(Model model,
                        HttpServletRequest request,
                        HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != "anonymousUser") {
            return "/error/403";
        }

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
                                 RedirectAttributes redirectAttributes,
                                 @ModelAttribute @Validated MemberFormDto memberForm,
                                 HttpSession session) {
        try {
            Member registerMember = memberService.registerUser
                    (
                            memberForm.getName(),
                            passwordEncoder.encode(memberForm.getPassword()),
                            memberForm.getEmail()
                    );
        }
        catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error",true);
            return "redirect:/user/register";
        }
        catch (RuntimeException e) {
            return "/error/500";
        }

        return "loginForm";
    }

    @GetMapping("/info")
    public String userInfo(Model model) {
        String username = model.getAttribute("username").toString();
        Member currentUser = memberService.findMember(username);

        model.addAttribute("kaiaAddress", currentUser.getKaiaAddress());

        return "userInfo";
    }
}

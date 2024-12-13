package com.davejang.blockchain_ticketing.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(value = "/")
public class HomeController {

    @GetMapping
    public String goToDashBoard(Model model,
                                Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();  // 로그인한 사용자의 이름
            model.addAttribute("username", username);  // 모델에 사용자 이름 추가
        }

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("isAdmin", true);
        }
        return "dashBoard";
    }
}

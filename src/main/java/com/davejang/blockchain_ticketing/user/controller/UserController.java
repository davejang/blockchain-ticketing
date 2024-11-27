package com.davejang.blockchain_ticketing.user.controller;

import com.davejang.blockchain_ticketing.common.utils.ClientUtils;
import com.davejang.blockchain_ticketing.user.domain.User;
import com.davejang.blockchain_ticketing.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
                               @Valid @ModelAttribute User user,
                               HttpSession session) {

        User registerUser = userService.registerUser(user);
        model.addAttribute("username", user.getName());
        return "loginSuccess";
    }
}

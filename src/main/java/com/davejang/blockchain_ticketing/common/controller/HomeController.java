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
    public String goToDashBoard(Model model) {
        return "dashBoard";
    }
}

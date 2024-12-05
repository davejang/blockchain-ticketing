package com.davejang.blockchain_ticketing.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "error")
public class ErrorController {

    @GetMapping("/401")
    public String error401() {
        return "/error/401";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}

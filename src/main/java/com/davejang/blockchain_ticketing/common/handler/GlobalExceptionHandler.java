package com.davejang.blockchain_ticketing.common.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;

@ControllerAdvice
@Deprecated
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentExceptions(Exception ex,
                                      @RequestHeader(value = "Referer", required = false) String referer,
                                      Model model) {

        model.addAttribute("errorType", ex.getClass().getSimpleName());
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("timestamp",System.currentTimeMillis());

        if(referer != null) {
            return "redirect:" + referer + "?error=true";
        }

        return "error";
    }
}

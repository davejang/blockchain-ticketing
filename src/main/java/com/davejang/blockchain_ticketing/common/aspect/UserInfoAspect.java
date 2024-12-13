package com.davejang.blockchain_ticketing.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Aspect
@Component
public class UserInfoAspect {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerMethods() {}

    @Before("controllerMethods() && args(model,..)")
    public void addUserInformationToModel(JoinPoint joinPoint, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String username = authentication.getName();  // 로그인한 사용자의 이름
            if (!username.equals("anonymousUser")){
                model.addAttribute("username", username);  // 모델에 사용자 이름 추가
            }
        }

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("isAdmin", true);
        }
    }
}

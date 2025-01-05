package com.davejang.blockchain_ticketing.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberFormDto {
    @NotBlank
    private String name;

    @NotBlank
    private String password;
    
    @NotBlank
    @Email(message = "이메일 형식이 잘못되었습니다!")
    private String email;
}

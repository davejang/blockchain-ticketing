package com.davejang.blockchain_ticketing.member.dto;

import jakarta.validation.constraints.NotBlank;

public class MemberFormDto {
    @NotBlank
    private String name;

    @NotBlank
    private String password;
}

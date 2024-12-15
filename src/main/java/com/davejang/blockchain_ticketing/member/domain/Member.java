package com.davejang.blockchain_ticketing.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String kaiaAddress;

    @Builder
    public Member(String name,
                  String password,
                  Role role,
                  String kaiaAddress) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.kaiaAddress = kaiaAddress;
    }
}

package com.davejang.blockchain_ticketing.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String kaiaAddress;

    private LocalDate registerDate;

    @Builder
    public Member(String name,
                  String password,
                  String email,
                  Role role,
                  String kaiaAddress,
                  LocalDate registerDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kaiaAddress = kaiaAddress;
        this.registerDate = registerDate;
    }
}

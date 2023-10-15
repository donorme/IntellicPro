package com.intellicpro.patent.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    private long id;
    private String code;
    private String name;

    private String password;
}

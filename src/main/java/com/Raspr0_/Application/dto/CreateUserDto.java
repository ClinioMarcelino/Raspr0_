package com.Raspr0_.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private Long id;

    private String name;
    private String cpf;
    private String email;
    private String phone;
}

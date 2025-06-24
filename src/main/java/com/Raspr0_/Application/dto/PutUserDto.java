package com.Raspr0_.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutUserDto {
    private Long id;

    private String name;
    private String cpf;
    private String email;
    private String phone;

}
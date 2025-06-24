package com.Raspr0_.Application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutDeviceDto {
    private Long id;
    private String model;
    private String version;
    private Long    serialNumber;

    private Long userId;
}

package com.Raspr0_.Application.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;
    private String version;
    private int    serialNumber;

    @ManyToOne
    @JoinColumn()
    @JsonBackReference
    private User user;
}

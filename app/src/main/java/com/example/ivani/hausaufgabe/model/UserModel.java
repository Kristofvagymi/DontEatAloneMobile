package com.example.ivani.hausaufgabe.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserModel implements Serializable{
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private String email;
    private String job;
    private String hobby;
    private String sex;
    private String phoneNumber;
    private int id;
}

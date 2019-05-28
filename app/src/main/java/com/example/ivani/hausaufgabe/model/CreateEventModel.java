package com.example.ivani.hausaufgabe.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEventModel {
    private int userId;
    private String title;
    private int numOfMaxParticipants;
    private String description;
    private String country;
    private String city;
    private String dateTime;
}

package com.example.ivani.hausaufgabe.model;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EventDetailedModel {
    private UserModel createdBy;
    private List<UserModel> participants;
    private String title;
    private String description;
    private String country;
    private String city;
    private String date;
    private int maxparticipants;
    private int id;


}

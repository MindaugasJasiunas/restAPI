package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class UserDetailsResponseModel {  // Response class to return JSON as response
    private UUID publicId;
    public String firstName;
    public String lastName;
    private String email;
}
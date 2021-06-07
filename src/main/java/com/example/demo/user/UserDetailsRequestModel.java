package com.example.demo.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserDetailsRequestModel {  // Request class used to convert JSON to Java(match JSON request)
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter

public class AuthenticationResponseModel {  //used for JWT response after signing in
    private final String jwt;
}

package com.example.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter

public class AuthenticationResponseModel {  //used for JWT response after signing in
    @ApiModelProperty(value="JSON Web Token used to manipulate users data", required=true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImV4cCI6MTYyNTgwOTIzMCwiaWF0IjoxNjI1NzczMjMwfQ.Ku2o9B2Hu82WCey1BVQRTnUnBVtgUnpJpHE0WTjaIgQ")
    private final String jwt;
}

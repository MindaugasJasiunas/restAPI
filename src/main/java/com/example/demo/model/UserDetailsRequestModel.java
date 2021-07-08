package com.example.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class UserDetailsRequestModel {  // Request class used to convert JSON to Java(match JSON request)
    @ApiModelProperty(value="User first name", required=false, example = "Tom")
    private String firstName;
    @ApiModelProperty(value="User last name", required=false, example = "Johnson")
    private String lastName;
    @ApiModelProperty(value="User email", required=true, example = "example@example.com")
    private String email;
    @ApiModelProperty(value="User password", required=true, example = "password")
    private String password;
}

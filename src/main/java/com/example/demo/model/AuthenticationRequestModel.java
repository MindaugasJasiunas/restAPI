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

public class AuthenticationRequestModel {  //used for signing in
    @NotNull
    @NotEmpty
    @ApiModelProperty(value="User email", required=true, example = "john.doe@example.com")
    private String email;
    @NotNull
    @NotEmpty
    @ApiModelProperty(value="User password", required=true, example = "encryptedPassword")
    private String password;
}

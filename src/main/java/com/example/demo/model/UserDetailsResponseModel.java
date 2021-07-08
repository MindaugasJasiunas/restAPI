package com.example.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class UserDetailsResponseModel {  // Response class to return JSON as response
    @ApiModelProperty(value="User public ID", required=true, example = "e03874a7-8dc6-4b3c-91b3-22b1212776bd")
    private UUID publicId;
    @ApiModelProperty(value="User first name", required=true, example = "Tom")
    private String firstName;
    @ApiModelProperty(value="User last name", required=true, example = "Johnson")
    private String lastName;
    @ApiModelProperty(value="User email", required=true, example = "example@example.com")
    private String email;
}

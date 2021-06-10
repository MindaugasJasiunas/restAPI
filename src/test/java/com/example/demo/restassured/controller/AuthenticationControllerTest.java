package com.example.demo.restassured.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//RestAssured
class AuthenticationControllerTest {
    private final String CONTEXT_PATH= "/api/v1";

    @BeforeEach
    void setUp(){
        //http://localhost:8080/authenticate
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;
    }

    @Test
    @DisplayName("Loging in with user 'john.doe@example.com:encryptedPassword' defined in application bootstrap class and getting JWT token")
    void createAuthenticationToken() {
            Map< String, Object> userLoginDetails= new HashMap<>();
            userLoginDetails.put("email","john.doe@example.com");
            userLoginDetails.put("password","encryptedPassword");

            Response response= RestAssured.given()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(userLoginDetails)
                    .when()
                    .post("/authenticate")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            assertNotNull(response.jsonPath().getString("jwt"));
    }

}
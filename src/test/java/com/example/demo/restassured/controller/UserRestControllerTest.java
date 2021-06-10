package com.example.demo.restassured.controller;

import com.example.demo.model.UserDetailsResponseModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//RestAssured

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRestControllerTest {
    private final String CONTEXT_PATH= "/api/v1";
    private static String jwtToken;
    private static String createdUserPublicId;
    private static String createdUserEmail;
    private static String createdUserFirstName;
    private static String createdUserLastName;

    @BeforeEach
    void setUp(){
        // http://localhost:8080/api/v1/users
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;
    }

    @Test
    @Order(1)
    @DisplayName("Get users")
    public void getUsers(){
        List users=new ArrayList<>();

        users = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(CONTEXT_PATH+"/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().body().as(users.getClass());

//        List<UserDetailsResponseModel> users= response.jsonPath().getList("$");

        assertEquals(3, users.size());
//        assertEquals(3, response.jsonPath().getList("$").size());
    }

    @Test
    @Order(2)
    @DisplayName("Login with user 'john.doe@example.com:encryptedPassword' defined in application bootstrap class")
    public void login(){
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

        jwtToken= response.jsonPath().getString("jwt");
    }

    @Test
    @Order(3)
    @DisplayName("Create new user using JWT token we got when logging in")
    public void createUser(){
        Map< String, Object> newUserDetails= new HashMap<>();
        //first name & last name from fake person generator
        newUserDetails.put("firstName","Edith");
        newUserDetails.put("lastName","Lehman");
        newUserDetails.put("email","edith.lehman@example.com");
        newUserDetails.put("password","newlyPasswordCreated");


        Response response= RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer "+jwtToken)
                .accept("application/json")
                .body(newUserDetails)
                .when()
                .post(CONTEXT_PATH+"/users")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract()
                .response();

        //parse JSON response
        String publicId= response.jsonPath().getString("publicId");
        String firstName= response.jsonPath().getString("firstName");
        String lastName= response.jsonPath().getString("lastName");
        String email= response.jsonPath().getString("email");

        createdUserPublicId=publicId;
        createdUserEmail=email;
        createdUserFirstName=firstName;
        createdUserLastName=lastName;

        assertEquals(newUserDetails.get("firstName"), firstName);
        assertEquals(newUserDetails.get("lastName"), lastName);
        assertEquals(newUserDetails.get("email"), email);
    }


    @Test
    @Order(4)
    @DisplayName("Get newly created user")
    public void getUser(){
        Response response= RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get(CONTEXT_PATH+"/users/"+createdUserPublicId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        //parse JSON response
        String publicId= response.jsonPath().getString("publicId");
        String firstName= response.jsonPath().getString("firstName");
        String lastName= response.jsonPath().getString("lastName");
        String email= response.jsonPath().getString("email");

        assertEquals(createdUserPublicId, publicId);
        assertEquals(createdUserEmail, email);
        assertEquals(createdUserFirstName, firstName);
        assertEquals(createdUserLastName, lastName);
    }


    @Test
    @Order(5)
    @DisplayName("Update created user")
    public void updateUser() {
        Map< String, Object> updateUserDetails= new HashMap<>();
        updateUserDetails.put("firstName","EdithUPDATED");
        updateUserDetails.put("lastName","LehmanUPDATED");
        updateUserDetails.put("email","edith.lehman@example.comUPDATED");
        updateUserDetails.put("password","newlyPasswordCreatedUPDATED");


        Response response= RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer "+jwtToken)
                .accept("application/json")
                .body(updateUserDetails)
                .when()
                .put(CONTEXT_PATH+"/users/"+createdUserPublicId)
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract()
                .response();

        //parse JSON response
        String publicId= response.jsonPath().getString("publicId");
        String firstName= response.jsonPath().getString("firstName");
        String lastName= response.jsonPath().getString("lastName");
        String email= response.jsonPath().getString("email");

        createdUserPublicId=publicId;
        createdUserEmail=email;
        createdUserFirstName=firstName;
        createdUserLastName=lastName;

        assertEquals(updateUserDetails.get("firstName"), firstName);
        assertEquals(updateUserDetails.get("lastName"), lastName);
        assertEquals(updateUserDetails.get("email"), email);
    }


    @Test
    @Order(6)
    @DisplayName("Partial update created user")
    public void partialUpdateUser() {
        //we could change just one or some of the fields but for test purposes -all
        Map< String, Object> partialUpdateUserDetails= new HashMap<>();
        partialUpdateUserDetails.put("firstName","EdithPartialUpdate");
        partialUpdateUserDetails.put("lastName","LehmanPartialUpdate");
        partialUpdateUserDetails.put("email","edith.lehman@example.comPartialUpdate");
        partialUpdateUserDetails.put("password","newlyPasswordCreatedPartialUpdate");


        Response response= RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer "+jwtToken)
                .accept("application/json")
                .body(partialUpdateUserDetails)
                .when()
                .patch(CONTEXT_PATH+"/users/"+createdUserPublicId)
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract()
                .response();

        //parse JSON response
        String publicId= response.jsonPath().getString("publicId");
        String firstName= response.jsonPath().getString("firstName");
        String lastName= response.jsonPath().getString("lastName");
        String email= response.jsonPath().getString("email");

        createdUserPublicId=publicId;
        createdUserEmail=email;
        createdUserFirstName=firstName;
        createdUserLastName=lastName;

        assertEquals(partialUpdateUserDetails.get("firstName"), firstName);
        assertEquals(partialUpdateUserDetails.get("lastName"), lastName);
        assertEquals(partialUpdateUserDetails.get("email"), email);
    }


    @Test
    @Order(7)
    @DisplayName("Delete created user")
    public void deleteUser() {
        Response response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(CONTEXT_PATH+"/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        //how many user objects there is
        int jsonObjectsCounter= response.jsonPath().getList("$").size();

        //delete created user
        RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer "+jwtToken)
                .accept("application/json")
                .when()
                .delete(CONTEXT_PATH+"/users/"+createdUserPublicId)
                .then()
                .statusCode(204)
                .extract()
                .response();


        //how many objects left
        response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(CONTEXT_PATH+"/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        assertEquals(jsonObjectsCounter-1, response.jsonPath().getList("$").size());  // 1 less
    }

}
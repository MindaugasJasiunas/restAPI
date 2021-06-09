package com.example.demo.controller;

import com.example.demo.error.UserCreationException;
import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import com.example.demo.user.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.OK) //200
    public Iterable<UserDetailsResponseModel> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(value = "/users/{publicId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK) //200
    public UserDetailsResponseModel getUser(@PathVariable("publicId") UUID publicId){
        return userService.getUser(publicId);
    }

    @PostMapping(value = "/users", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)  // 201
    public UserDetailsResponseModel createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel){
        return userService.createOrUpdateUser(null, userDetailsRequestModel);
    }

    @PutMapping(value = "/users/{publicId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)  // 201 OR @ResponseStatus(HttpStatus.NO_CONTENT) //204 if not returning
    public UserDetailsResponseModel updateUser(@PathVariable("publicId") UUID publicId, @RequestBody UserDetailsRequestModel userDetailsRequestModel){
        if(userDetailsRequestModel.getEmail()!=null){
            return userService.createOrUpdateUser(publicId, userDetailsRequestModel);
        }else{
            throw new UserCreationException("Email is required.");
        }
    }

    @PatchMapping(value = "/users/{publicId}", consumes = "application/json", produces = "application/json") //partial update
    @ResponseStatus(HttpStatus.CREATED)  // 201
    public UserDetailsResponseModel partialUpdateUser(@PathVariable("publicId") UUID publicId, @RequestBody UserDetailsRequestModel userDetailsRequestModel){
        return userService.partialUpdateUser(publicId, userDetailsRequestModel);
    }

    @DeleteMapping("/users/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204
    public void deleteUser(@PathVariable("publicId") UUID publicId){
        try{
            userService.deleteUser(publicId);
        }catch(EmptyResultDataAccessException e){
            // prevent error if ID doesn't exist
        }
    }
}

package com.example.demo.controller;

import com.example.demo.documentation.SwaggerConfiguration;
import com.example.demo.error.UserCreationException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import com.example.demo.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = { SwaggerConfiguration.TAG_USER_CONTROLLER_DESCRIPTION })
@RestController
@RequestMapping("/api/v1/")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value="Get all users", notes="This endpoint will get all users from database.")
    @GetMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.OK) //200
    public Iterable<UserDetailsResponseModel> getUsers(){
        return userService.getUsers();
    }

    @ApiOperation(value="Get user by public ID", notes="This endpoint will get one user from database by user public ID.")
    @GetMapping(value = "/users/{publicId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK) //200
    public UserDetailsResponseModel getUser(@PathVariable("publicId") UUID publicId){
        return userService.getUser(publicId);
    }

    @ApiOperation(value="Create new user and save to DB", notes="This endpoint is used to create new user and save it to database.")
    @ApiImplicitParams({@ApiImplicitParam(name="authorization", value="Bearer JWT Token", paramType="header")})
    @PostMapping(value = "/users", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)  // 201
    public UserDetailsResponseModel createUser(@RequestBody @Valid UserDetailsRequestModel userDetailsRequestModel){
        return userService.createOrUpdateUser(null, userDetailsRequestModel);
    }

    @ApiOperation(value="Update existing user and save to DB", notes="This endpoint is used to update existing user in database.")
    @ApiImplicitParams({@ApiImplicitParam(name="authorization", value="Bearer JWT Token", paramType="header")})
    @PutMapping(value = "/users/{publicId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)  // 201 OR @ResponseStatus(HttpStatus.NO_CONTENT) //204 if not returning
    public UserDetailsResponseModel updateUser(@PathVariable("publicId") UUID publicId, @RequestBody @Valid UserDetailsRequestModel userDetailsRequestModel){
        if(!userService.userExistsByUUID(publicId)){
            throw new UserCreationException("User with public Id "+publicId+" doesn't exist.");
        }
        if(userDetailsRequestModel.getEmail()!=null){
            return userService.createOrUpdateUser(publicId, userDetailsRequestModel);
        }else{
            throw new UserCreationException("Email is required.");
        }
    }

    @ApiOperation(value="Partial update existing user and save to DB", notes="This endpoint is used to partially update(doesn't require to update all fields) existing user in database. Requires ONLY publicId. All fields are OPTIONAL.")
    @ApiImplicitParams({@ApiImplicitParam(name="authorization", value="Bearer JWT Token", paramType="header")})
    @PatchMapping(value = "/users/{publicId}", consumes = "application/json", produces = "application/json") //partial update
    @ResponseStatus(HttpStatus.CREATED)  // 201
    public UserDetailsResponseModel partialUpdateUser(@PathVariable("publicId") UUID publicId, @RequestBody @Valid UserDetailsRequestModel userDetailsRequestModel){
        return userService.partialUpdateUser(publicId, userDetailsRequestModel);
    }

    @ApiOperation(value="Delete user from DB by public ID", notes="This endpoint is used to delete existing user from DB by user public ID.")
    @ApiImplicitParams({@ApiImplicitParam(name="authorization", value="Bearer JWT Token", paramType="header")})
    @DeleteMapping("/users/{publicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204
    public void deleteUser(@PathVariable("publicId") UUID publicId){
        try{
            userService.deleteUser(publicId);
        }catch(EmptyResultDataAccessException e){
            // prevent error if ID doesn't exist
            throw new UserNotFoundException("User with public id " +publicId+" doesn't exist.");
        }
    }
}

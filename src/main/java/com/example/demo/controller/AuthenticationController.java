package com.example.demo.controller;

import com.example.demo.documentation.SwaggerConfiguration;
import com.example.demo.model.AuthenticationRequestModel;
import com.example.demo.model.AuthenticationResponseModel;
import com.example.demo.user.UserService;
import com.example.demo.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = { SwaggerConfiguration.TAG_AUTH_CONTROLLER_DESCRIPTION })
@RestController
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @ApiOperation(value="Authenticate user to get JWT", notes="This endpoint is used to authenticate by user email & password. In return JSON Web Token is sent. This token is used to manipulate users(POST, PUT, PATCH, DELETE endpoints).")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public AuthenticationResponseModel createAuthenticationToken(@RequestBody @Valid AuthenticationRequestModel request) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password ", e);
        }
        //if authenticated - create JWT
        final UserDetails userDetails= userService.loadUserByUsername(request.getEmail());
        final String jwt= jwtUtil.generateToken(userDetails);

        //return JWT as JSON
        AuthenticationResponseModel response= new AuthenticationResponseModel(jwt);
        return response;
    }
}

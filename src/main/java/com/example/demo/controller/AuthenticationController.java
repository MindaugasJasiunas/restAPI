package com.example.demo.controller;

import com.example.demo.model.AuthenticationRequestModel;
import com.example.demo.model.AuthenticationResponseModel;
import com.example.demo.user.UserService;
import com.example.demo.util.JwtUtil;
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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid AuthenticationRequestModel request) throws Exception{
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
        return ResponseEntity.ok(response);
    }
}

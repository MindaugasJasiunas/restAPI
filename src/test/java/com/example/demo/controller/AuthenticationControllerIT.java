package com.example.demo.controller;

import com.example.demo.TestUtilities;
import com.example.demo.model.AuthenticationRequestModel;
import com.example.demo.model.AuthenticationResponseModel;
import com.example.demo.user.UserService;
import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerIT {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserService userService;
    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    AuthenticationController controller;


    @DisplayName("create authentication token and return")
    @Test
    void createAuthenticationToken() {
        AuthenticationRequestModel requestModel= TestUtilities.generateAuthenticationRequestModel();
        String fakeJwt="abc";
        AuthenticationResponseModel responseModel= new AuthenticationResponseModel(fakeJwt);

        UserDetails userDetails = Mockito.mock(UserDetails.class);

        Mockito.when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        Mockito.when(jwtUtil.generateToken(userDetails)).thenReturn(fakeJwt);
        try {
            AuthenticationResponseModel response= controller.createAuthenticationToken(requestModel);
            assertNotNull(response);
            assertEquals(fakeJwt, response.getJwt());
        }catch (Exception e){
            fail("Exception thrown when generating JWT token.");
        }

    }
}
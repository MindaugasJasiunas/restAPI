package com.example.demo.controller;

import com.example.demo.TestUtilities;
import com.example.demo.error.UserCreationException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import com.example.demo.user.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserRestControllerIT {
    @Mock
    UserService userService;
    @InjectMocks
    UserRestController controller;


    @DisplayName("get users")
    @Test
    void getUsers() {
        //given
        Mockito.when(userService.getUsers()).thenReturn(TestUtilities.generateUserDetailsResponseModelsList());

        //when
        Iterable<UserDetailsResponseModel> userDetails= controller.getUsers();

        //then
        assertNotNull(userDetails);
        assertEquals(2, ((ArrayList<UserDetailsResponseModel>) userDetails).size());
        Mockito.verify(userService, Mockito.times(1)).getUsers();
    }


    @DisplayName("get user")
    @Test
    void getUser() {
        //given
        UserDetailsResponseModel model= TestUtilities.generateUserDetailsResponseModel();
        Mockito.when(userService.getUser(any(UUID.class))).thenReturn(model);

        //when
        UserDetailsResponseModel userDetailsResponseModel= controller.getUser(UUID.randomUUID());

        //then
        assertNotNull(userDetailsResponseModel);
        assertEquals(model.getPublicId(),userDetailsResponseModel.getPublicId());
        assertEquals(model.getEmail(),userDetailsResponseModel.getEmail());
        assertEquals(model.getFirstName(),userDetailsResponseModel.getFirstName());
        assertEquals(model.getLastName(),userDetailsResponseModel.getLastName());

        Mockito.verify(userService, Mockito.times(1)).getUser(any(UUID.class));
    }


    @DisplayName("create user")
    @Test
    void createUser() {
        //given
        UserDetailsRequestModel userDetailsRequestModel= TestUtilities.generateUserDetailsRequestModel();
        UserDetailsResponseModel userDetailsResponseModel= TestUtilities.generateUserDetailsResponseModel();

        Mockito.when(userService.createOrUpdateUser(null, userDetailsRequestModel)).thenReturn(userDetailsResponseModel);

        //when
        UserDetailsResponseModel responseModel= controller.createUser(userDetailsRequestModel);

        //then
        assertNotNull(responseModel);
        assertEquals(userDetailsResponseModel.getPublicId(), responseModel.getPublicId());
        assertEquals(userDetailsResponseModel.getEmail(), responseModel.getEmail());
        assertEquals(userDetailsResponseModel.getFirstName(), responseModel.getFirstName());
        assertEquals(userDetailsResponseModel.getLastName(), responseModel.getLastName());

        Mockito.verify(userService, Mockito.times(1)).createOrUpdateUser(null, userDetailsRequestModel);
    }


    @DisplayName("update user")
    @Test
    void updateUser() {
        //given
        UserDetailsRequestModel model= TestUtilities.generateUserDetailsRequestModel();
        UserDetailsResponseModel respModel= TestUtilities.generateUserDetailsResponseModel();
        Mockito.when(userService.userExistsByUUID(any(UUID.class))).thenReturn(true);
        Mockito.when(userService.createOrUpdateUser(any(UUID.class), any(UserDetailsRequestModel.class))).thenReturn(respModel);

        //when
        UserDetailsResponseModel responseModel= controller.updateUser(UUID.randomUUID(), model);

        //then
        assertNotNull(responseModel);
        assertEquals(respModel.getPublicId(), responseModel.getPublicId());
        assertEquals(respModel.getEmail(), responseModel.getEmail());
        assertEquals(respModel.getFirstName(), responseModel.getFirstName());
        assertEquals(respModel.getLastName(), responseModel.getLastName());
        Mockito.verify(userService, Mockito.times(1)).userExistsByUUID(any(UUID.class));
        Mockito.verify(userService, Mockito.times(1)).createOrUpdateUser(any(UUID.class), any(UserDetailsRequestModel.class));
    }

    @DisplayName("update user - no email provided. Expecting UserCreationException")
    @Test
    void updateUser_noEmailProvided() {
        UserDetailsRequestModel model= TestUtilities.generateUserDetailsRequestModel();
        model.setEmail(null);
        Mockito.when(userService.userExistsByUUID(any(UUID.class))).thenReturn(true);

        assertThrows(UserCreationException.class, ()->controller.updateUser(UUID.randomUUID(), model));

        Mockito.verify(userService, Mockito.times(1)).userExistsByUUID(any(UUID.class));
    }


    @DisplayName("update user - user doesn't exist. Expecting UserCreationException")
    @Test
    void updateUser_notExist() {
        UserDetailsRequestModel model= TestUtilities.generateUserDetailsRequestModel();
        model.setEmail(null);
        Mockito.when(userService.userExistsByUUID(any(UUID.class))).thenReturn(false);

        assertThrows(UserCreationException.class, ()->controller.updateUser(UUID.randomUUID(), model));

        Mockito.verify(userService, Mockito.times(1)).userExistsByUUID(any(UUID.class));
    }


    @DisplayName("partial update user")
    @Test
    void partialUpdateUser() {
        //given
        UserDetailsRequestModel requestModel= TestUtilities.generateUserDetailsRequestModel();
        UserDetailsResponseModel responseModel= TestUtilities.generateUserDetailsResponseModel();
        Mockito.when(userService.partialUpdateUser(any(UUID.class), any(UserDetailsRequestModel.class))).thenReturn(responseModel);

        //when
        UserDetailsResponseModel model= controller.partialUpdateUser(UUID.randomUUID(), requestModel);

        //then
        assertNotNull(model);
        assertEquals(responseModel.getPublicId(), model.getPublicId());
        assertEquals(responseModel.getEmail(), model.getEmail());
        assertEquals(responseModel.getFirstName(), model.getFirstName());
        assertEquals(responseModel.getLastName(), model.getLastName());
    }


    @DisplayName("deleting user")
    @Test
    void deleteUser() {
        //given
        Mockito.doNothing().when(userService).deleteUser(any(UUID.class));

        //when
        controller.deleteUser(UUID.randomUUID());

        //then
        Mockito.verify(userService, Mockito.times(1)).deleteUser(any(UUID.class));
    }

    @DisplayName("deleting user - username not found. Expecting UserNotFoundException")
    @Test
    void deleteUser_usernameNotFound() {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(userService).deleteUser(any(UUID.class));

        assertThrows(UserNotFoundException.class, ()-> controller.deleteUser(UUID.randomUUID()) );

        Mockito.verify(userService, Mockito.times(1)).deleteUser(any(UUID.class));
    }
}
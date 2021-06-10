package com.example.demo.user;

import com.example.demo.TestUtilities;
import com.example.demo.converters.UserEntityUserResponseMapper;
import com.example.demo.error.UserCreationException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //initializes mocks
class UserServiceImplIT {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;


    @DisplayName("Get users")
    @Test
    void getUsers() {
        Iterable<UserEntity> userEntityIterable= TestUtilities.generateUserEntitiesList();
        ArrayList<UserEntity> userEntityArrayList= (ArrayList<UserEntity>) userEntityIterable;

        Mockito.when(userRepository.findAll()).thenReturn(userEntityIterable);

        Iterable<UserDetailsResponseModel> found= userService.getUsers();

        assertNotNull(found);

        found.forEach(userDetailsResponseModel -> {
            if(userDetailsResponseModel.getPublicId().equals(userEntityArrayList.get(0).getPublicId())){
                assertEquals(userDetailsResponseModel.getPublicId(), userEntityArrayList.get(0).getPublicId());
                assertEquals(userDetailsResponseModel.getEmail(), userEntityArrayList.get(0).getEmail());
                assertEquals(userDetailsResponseModel.getFirstName(), userEntityArrayList.get(0).getFirstName());
                assertEquals(userDetailsResponseModel.getLastName(), userEntityArrayList.get(0).getLastName());
            }else{
                assertEquals(userDetailsResponseModel.getPublicId(), userEntityArrayList.get(1).getPublicId());
                assertEquals(userDetailsResponseModel.getEmail(), userEntityArrayList.get(1).getEmail());
                assertEquals(userDetailsResponseModel.getFirstName(), userEntityArrayList.get(1).getFirstName());
                assertEquals(userDetailsResponseModel.getLastName(), userEntityArrayList.get(1).getLastName());
            }
        });

        assertEquals(2, ((ArrayList<UserDetailsResponseModel>)found).size());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }


    @DisplayName("Get user")
    @Test
    void getUser() {
        UserEntity userEntity1= TestUtilities.generateUserEntity();

        Mockito.when(userRepository.findUserEntityByPublicId(userEntity1.getPublicId())).thenReturn(Optional.of(userEntity1));

        UserDetailsResponseModel found= userService.getUser(userEntity1.getPublicId());

        assertNotNull(found);

        assertEquals(userEntity1.getPublicId(), found.getPublicId());
        assertEquals(userEntity1.getEmail(), found.getEmail());
        assertEquals(userEntity1.getFirstName(), found.getFirstName());
        assertEquals(userEntity1.getLastName(), found.getLastName());

        Mockito.verify(userRepository, Mockito.times(1)).findUserEntityByPublicId(any(UUID.class));
    }


    @DisplayName("Get user - public ID doesn't exist. UserNotFoundException expected")
    @Test
    void getUser_publicIdDoesntExist_throwUserNotFoundException() {
        Mockito.when(userRepository.findUserEntityByPublicId(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(UUID.randomUUID()));

        Mockito.verify(userRepository, Mockito.times(1)).findUserEntityByPublicId(any(UUID.class));
    }


    @DisplayName("Create new user")
    @Test
    void createOrUpdateUser_create() {  // createOrUpdateUser_create_EmailAlreadyExists catch exception
        UserDetailsRequestModel model= TestUtilities.generateUserDetailsRequestModel();

        UserEntity userEntity1= TestUtilities.generateUserEntity();


        final ArgumentCaptor< UserEntity> captor= ArgumentCaptor.forClass(UserEntity.class);

        Mockito.when(userRepository.save(captor.capture())).thenReturn(userEntity1);

        UserDetailsResponseModel responseModel= userService.createOrUpdateUser(null, model);
        assertNotNull(responseModel);

        //test attributes sent in UserDetailsRequestModel & returned UserDetailsResponseModel
        assertEquals(userEntity1.getFirstName(), responseModel.getFirstName());
        assertEquals(userEntity1.getLastName(), responseModel.getLastName());
        assertEquals(userEntity1.getEmail(), responseModel.getEmail());


        UserEntity userToSave= captor.getValue();
        assertNotNull(userToSave);
        //test attributes in converted UserEntity to save
        assertEquals(userEntity1.getFirstName(), userToSave.getFirstName());
        assertEquals(userEntity1.getLastName(), userToSave.getLastName());
        assertEquals(userEntity1.getEmail(), userToSave.getEmail());
        assertEquals(userEntity1.getEncryptedPassword(), userToSave.getEncryptedPassword());
        assertEquals(false, userToSave.isEmailVerificationStatus());

        Mockito.verify(userRepository, Mockito.times(1)).save(userToSave);
        //never called method used in update
        Mockito.verify(userRepository, Mockito.never()).findUserEntityByPublicId(any(UUID.class));
    }


    @DisplayName("Create user - email already exists UserCreationException expected")
    @Test
    void createOrUpdateUser_create_EmailAlreadyExistsException() {
        UserDetailsRequestModel model=TestUtilities.generateUserDetailsRequestModel();

        UserEntity userEntity1= TestUtilities.generateUserEntity();

        final ArgumentCaptor< UserEntity> captor= ArgumentCaptor.forClass(UserEntity.class);

        //simulate email already exists
        Mockito.when(userRepository.save(captor.capture())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(UserCreationException.class, () ->userService.createOrUpdateUser(null, model) );


        UserEntity userToSave= captor.getValue();
        assertNotNull(userToSave);
        //test attributes in converted UserEntity to save
        assertEquals(userEntity1.getFirstName(), userToSave.getFirstName());
        assertEquals(userEntity1.getLastName(), userToSave.getLastName());
        assertEquals(userEntity1.getEmail(), userToSave.getEmail());
        assertEquals(userEntity1.getEncryptedPassword(), userToSave.getEncryptedPassword());
        assertEquals(false, userToSave.isEmailVerificationStatus());

        Mockito.verify(userRepository, Mockito.times(1)).save(userToSave);
        //never called method used in update
        Mockito.verify(userRepository, Mockito.never()).findUserEntityByPublicId(any(UUID.class));
    }


    @DisplayName("Update user")
    @Test
    void createOrUpdateUser_update() {
        UserDetailsRequestModel model=TestUtilities.generateUserDetailsRequestModel();

        UserEntity userEntity1= TestUtilities.generateUserEntity();

        final ArgumentCaptor< UserEntity> captor= ArgumentCaptor.forClass(UserEntity.class);

        Mockito.when(userRepository.findUserEntityByPublicId(userEntity1.getPublicId())).thenReturn(Optional.of(userEntity1));
        Mockito.when(userRepository.save(captor.capture())).thenReturn(userEntity1);

        //updating
        UserDetailsResponseModel responseModel= userService.createOrUpdateUser(userEntity1.getPublicId(), model);
        assertNotNull(responseModel);

        UserEntity userToSave= captor.getValue();
        assertNotNull(userToSave);
        //test attributes in converted UserEntity to save
        assertEquals(model.getFirstName(), userToSave.getFirstName());
        assertEquals(model.getLastName(), userToSave.getLastName());
        assertEquals(model.getEmail(), userToSave.getEmail());
        assertEquals(model.getPassword(), userToSave.getEncryptedPassword());

        //fields checked when updating
        assertEquals(userEntity1.getId(), userToSave.getId());
        assertEquals(userEntity1.getPublicId(), userToSave.getPublicId());
        assertEquals(true, userToSave.isEmailVerificationStatus());


        Mockito.verify(userRepository, Mockito.times(2)).findUserEntityByPublicId(any(UUID.class));  // isPresent & actual
        Mockito.verify(userRepository, Mockito.times(1)).save(userToSave);
    }

    @DisplayName("Partial update user")
    @Test
    void partialUpdateUser() {
        UserDetailsRequestModel model=TestUtilities.generateUserDetailsRequestModel();

        UserEntity userEntity= TestUtilities.generateUserEntity();

        Mockito.when(userRepository.findUserEntityByPublicId(userEntity.getPublicId())).thenReturn(Optional.of(userEntity));

        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDetailsResponseModel response= userService.partialUpdateUser(userEntity.getPublicId(), model);

        assertEquals(userEntity.getPublicId(), response.getPublicId());
        assertEquals(model.getFirstName(), userEntity.getFirstName());
        assertEquals(model.getLastName(), userEntity.getLastName());
        assertEquals(model.getEmail(), userEntity.getEmail());
        assertEquals(model.getPassword(), userEntity.getEncryptedPassword());

        //check if response has fields
        assertEquals(model.getFirstName(), response.getFirstName());
        assertEquals(model.getLastName(), response.getLastName());
        assertEquals(model.getEmail(), response.getEmail());
    }

    @DisplayName("Partial update non existent user. Expecting UserNotFoundException")
    @Test
    void partialUpdateUser_userDoesntExist() { // + throws
        UserDetailsRequestModel model = TestUtilities.generateUserDetailsRequestModel();

        UserEntity userEntity = TestUtilities.generateUserEntity();

        Mockito.when(userRepository.findUserEntityByPublicId(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> userService.partialUpdateUser(UUID.randomUUID(), model));

        Mockito.verify(userRepository, Mockito.times(1)).findUserEntityByPublicId(any(UUID.class));
    }


    @DisplayName("Delete user")
    @Test
    void deleteUser() {
        Mockito.doNothing().when(userRepository).deleteUserEntityByPublicId(any(UUID.class));

        userService.deleteUser(UUID.randomUUID());

        Mockito.verify(userRepository, Mockito.times(1)).deleteUserEntityByPublicId(any(UUID.class));

    }

    @DisplayName("userExistsByUUID - user found")
    @Test
    void userExistsByUUID(){
        Mockito.when(userRepository.findUserEntityByPublicId(any(UUID.class))).thenReturn(Optional.of(TestUtilities.generateUserEntity()));
        assertTrue(userService.userExistsByUUID(UUID.randomUUID()));
        Mockito.verify(userRepository, Mockito.times(1)).findUserEntityByPublicId(any(UUID.class));
    }

    @DisplayName("userExistsByUUID - user not found")
    @Test
    void userExistsByUUID_no_user(){
        Mockito.when(userRepository.findUserEntityByPublicId(any(UUID.class))).thenReturn(Optional.empty());
        assertFalse(userService.userExistsByUUID(UUID.randomUUID()));
        Mockito.verify(userRepository, Mockito.times(1)).findUserEntityByPublicId(any(UUID.class));
    }


    @DisplayName("Load user by username(email)")
    @Test
    void loadUserByUsername() {
        UserEntity userEntity1= TestUtilities.generateUserEntity();

        Mockito.when(userRepository.findUserEntityByEmail(anyString())).thenReturn(Optional.of(userEntity1));

        UserDetails found= userService.loadUserByUsername(userEntity1.getEmail());

        assertNotNull(found);
        assertEquals(userEntity1.getEmail(), found.getUsername());
        assertEquals(userEntity1.getEncryptedPassword(), found.getPassword());

        Mockito.verify(userRepository, Mockito.times(2)).findUserEntityByEmail(anyString());  // 2 times - if present & actual

    }

    @DisplayName("Load user by username(email) - username not exist. UsernameNotFoundException expected.")
    @Test
    void loadUserByUsername_usernameNotFoundException() {
        UserEntity userEntity1= TestUtilities.generateUserEntity();

        Mockito.when(userRepository.findUserEntityByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(userEntity1.getEmail()));

        Mockito.verify(userRepository, Mockito.times(1)).findUserEntityByEmail(anyString());
    }
}
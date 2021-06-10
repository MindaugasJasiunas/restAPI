package com.example.demo;

import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import com.example.demo.user.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtilities {

    public static UserEntity generateUserEntity(){
        UserEntity userEntity1= new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setPublicId(UUID.randomUUID());
        userEntity1.setEmail("john.doe@example.com");
        userEntity1.setFirstName("John");
        userEntity1.setLastName("Doe");
        userEntity1.setEncryptedPassword("encryptedPassword");
        userEntity1.setEmailVerificationToken("ABCDEFG");
        userEntity1.setEmailVerificationStatus(true);
        userEntity1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userEntity1.setLastModifiedAt(new Timestamp(System.currentTimeMillis()+3600000));
        return userEntity1;
    }

    public static List<UserEntity> generateUserEntitiesList(){
        List<UserEntity> userEntities= new ArrayList<>();

        UserEntity userEntity1= new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setPublicId(UUID.randomUUID());
        userEntity1.setEmail("john.doe@example.com");
        userEntity1.setFirstName("John");
        userEntity1.setLastName("Doe");
        userEntity1.setEncryptedPassword("encryptedPassword");
        userEntity1.setEmailVerificationToken("ABCDEFG");
        userEntity1.setEmailVerificationStatus(true);
        userEntity1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userEntity1.setLastModifiedAt(new Timestamp(System.currentTimeMillis()+3600000));
        userEntities.add(userEntity1);

        UserEntity userEntity2= new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setPublicId(UUID.randomUUID());
        userEntity2.setEmail("jane.does@example.com");
        userEntity2.setFirstName("Jane");
        userEntity2.setLastName("Does");
        userEntity2.setEncryptedPassword("password");
        userEntity2.setEmailVerificationToken("GFEDCBA");
        userEntity2.setEmailVerificationStatus(true);
        userEntity2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userEntity2.setLastModifiedAt(new Timestamp(System.currentTimeMillis()+3600000));
        userEntities.add(userEntity2);

        return userEntities;
    }

    public static UserDetailsRequestModel generateUserDetailsRequestModel(){
        UserDetailsRequestModel model=new UserDetailsRequestModel();
        model.setEmail("john.doe@example.com");
        model.setPassword("encryptedPassword");
        model.setFirstName("John");
        model.setLastName("Doe");
        return model;
    }

    public static UserDetailsResponseModel generateUserDetailsResponseModel(){
        UserDetailsResponseModel model=new UserDetailsResponseModel();
        model.setPublicId(UUID.randomUUID());
        model.setEmail("john.doe@example.com");
        model.setFirstName("John");
        model.setLastName("Doe");
        return model;
    }

    public static Iterable<UserDetailsResponseModel> generateUserDetailsResponseModelsList(){
        List<UserDetailsResponseModel> userDetailsResponseModels= new ArrayList<>();

        UserDetailsResponseModel model=new UserDetailsResponseModel();
        model.setPublicId(UUID.randomUUID());
        model.setEmail("john.doe@example.com");
        model.setFirstName("John");
        model.setLastName("Doe");
        userDetailsResponseModels.add(model);

        UserDetailsResponseModel model2=new UserDetailsResponseModel();
        model2.setPublicId(UUID.randomUUID());
        model2.setEmail("jane.does@example.com");
        model2.setFirstName("Jane");
        model2.setLastName("Does");
        userDetailsResponseModels.add(model2);

        return userDetailsResponseModels;
    }

}

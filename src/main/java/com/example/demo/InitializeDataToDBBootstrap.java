package com.example.demo;

import com.example.demo.entity.BaseEntity;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j

@Component
public class InitializeDataToDBBootstrap implements CommandLineRunner {
    @Autowired
    private UserRepository userRepo;

    @Override
    public void run(String... args) throws Exception {
        createUsers();
    }
        
    public void createUsers(){
        UserEntity userEntity=new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEncryptedPassword("encryptedPassword");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setEmailVerificationStatus(true);
        userEntity.setEmailVerificationToken("ABCDEFG");
        BaseEntity savedToDB= userRepo.save(userEntity);
        log.debug("User saved to DB :"+savedToDB+" , with ID: "+savedToDB.getId()+" , with public ID: "+savedToDB.getPublicId());

        UserEntity userEntity2=new UserEntity();
        userEntity2.setFirstName("Jane");
        userEntity2.setLastName("Doe");
        userEntity2.setEncryptedPassword("password123");
        userEntity2.setEmail("jane.doe@@example.com");
        userEntity2.setEmailVerificationStatus(true);
        userEntity2.setEmailVerificationToken("QWERTY");
        BaseEntity savedToDB2= userRepo.save(userEntity2);
        log.debug("User saved to DB :"+savedToDB2+" , with ID: "+savedToDB2.getId()+" , with public ID: "+savedToDB2.getPublicId());

        UserEntity userEntity3=new UserEntity();
        userEntity3.setFirstName("Oscar");
        userEntity3.setLastName("Dean");
        userEntity3.setEncryptedPassword("encryptedPasswordCantBeSeen");
        userEntity3.setEmail("oscar.dean@example.com");
        userEntity3.setEmailVerificationStatus(true);
        userEntity3.setEmailVerificationToken("UJNMKJH");
        BaseEntity savedToDB3= userRepo.save(userEntity3);
        log.debug("User saved to DB :"+savedToDB3+" , with ID: "+savedToDB3.getId()+" , with public ID: "+savedToDB3.getPublicId());

    }
    
}

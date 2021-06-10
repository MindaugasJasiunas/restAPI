package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    private UserEntity userEntity;

    private final Long ID= 1L;
    private final UUID GENERATED_UUID= UUID.randomUUID();
    private final Timestamp CREATED_AT= new Timestamp(System.currentTimeMillis());
    private final Timestamp UPDATED_AT= new Timestamp(System.currentTimeMillis()+3600000);  // +1hour

    private final String FIRST_NAME= "John";
    private final String LAST_NAME= "Doe";
    private final String EMAIL= "john.doe@example.com";
    private final String PASSWORD= "encryptedPassword";
    private final String EMAIL_VERIFICATION_TOKEN= "ABCDEFG";
    private final boolean EMAIL_VERIFICATION_STATUS= true;

    private final String TEST_VAR= "Test";
    private final String TEST_VAR_EMAIL= "example@example.com";

    @BeforeEach
    void setUp(){
        userEntity=new UserEntity();

        //BaseEntity fields
        userEntity.setId(ID);
        userEntity.setPublicId(GENERATED_UUID);
        userEntity.setCreatedAt(CREATED_AT);
        userEntity.setLastModifiedAt(UPDATED_AT);
        //UserEntity fields
        userEntity.setFirstName(FIRST_NAME);
        userEntity.setLastName(LAST_NAME);
        userEntity.setEmail(EMAIL);
        userEntity.setEncryptedPassword(PASSWORD);
        userEntity.setEmailVerificationToken(EMAIL_VERIFICATION_TOKEN);
        userEntity.setEmailVerificationStatus(EMAIL_VERIFICATION_STATUS);
    }

    @Test
    void isNew() {
        assertEquals(false, userEntity.isNew());
        userEntity.setId(null);
        assertEquals(true, userEntity.isNew());
    }

    @Test
    void getFirstName() {
        assertEquals(FIRST_NAME, userEntity.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals(LAST_NAME, userEntity.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals(EMAIL, userEntity.getEmail());
    }

    @Test
    void getEncryptedPassword() {
        assertEquals(PASSWORD, userEntity.getEncryptedPassword());
    }

    @Test
    void getEmailVerificationToken() {
        assertEquals(EMAIL_VERIFICATION_TOKEN, userEntity.getEmailVerificationToken());
    }

    @Test
    void isEmailVerificationStatus() {
        assertEquals(EMAIL_VERIFICATION_STATUS, userEntity.isEmailVerificationStatus());
    }

    @Test
    void setFirstName() {
        userEntity.setFirstName(TEST_VAR);
        assertEquals(TEST_VAR, userEntity.getFirstName());
    }

    @Test
    void setLastName() {
        userEntity.setLastName(TEST_VAR);
        assertEquals(TEST_VAR, userEntity.getLastName());
    }

    @Test
    void setEmail() {
        userEntity.setEmail(TEST_VAR_EMAIL);
        assertEquals(TEST_VAR_EMAIL, userEntity.getEmail());
    }

    @Test
    void setEncryptedPassword() {
        userEntity.setEncryptedPassword(TEST_VAR);
        assertEquals(TEST_VAR, userEntity.getEncryptedPassword());
    }

    @Test
    void setEmailVerificationToken() {
        userEntity.setEmailVerificationToken(TEST_VAR);
        assertEquals(TEST_VAR, userEntity.getEmailVerificationToken());
    }

    @Test
    void setEmailVerificationStatus() {
        userEntity.setEmailVerificationStatus(true);
        assertEquals(true, userEntity.isEmailVerificationStatus());
    }
}
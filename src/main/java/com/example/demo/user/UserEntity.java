package com.example.demo.user;

import com.example.demo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data

@Entity(name = "User")
@Table(name = "user")
public class UserEntity extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;
    private String emailVerificationToken;
    private boolean emailVerificationStatus=false;

}

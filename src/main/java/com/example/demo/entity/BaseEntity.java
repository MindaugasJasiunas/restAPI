package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter

@MappedSuperclass
public class BaseEntity implements Serializable{
    private static final long serialVersionUID=1L;

    @Id
    @SequenceGenerator( name = "userentity_sequence", sequenceName = "userentity_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "userentity_sequence")
    private Long id;

    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false, unique = true)
    private UUID publicId= UUID.randomUUID();

    // If there is a @Version field Spring Data will test that value to determine if the entity is new or not. If the @Version field is not a primitive and is null then the entity is considered new.
    // SQL Error: 23505, SQLState: 23505
    //Unique index or primary key violation: "PUBLIC.UK_FUQKA493LLH9K0IMXQ2NQKBY_INDEX_2 ON PUBLIC.USER(PUBLIC_ID) VALUES 5"; SQL statement:
    //insert into user (created_at, last_modified_at, public_id, version, email, email_verification_status, email_verification_token, encrypted_password, first_name, last_name, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) [23505-200]
//    @Version
//    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp lastModifiedAt;

    public boolean isNew(){
        return this.id==null;
    }
}

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
    @SequenceGenerator( name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;

    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false, unique = true)
    private UUID publicId= UUID.randomUUID();

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp lastModifiedAt;

    public boolean isNew(){
        return this.id==null;
    }
}

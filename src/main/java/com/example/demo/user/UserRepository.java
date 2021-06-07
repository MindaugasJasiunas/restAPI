package com.example.demo.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByPublicId(UUID publicId);

    @Transactional //(service method needs @Transactional too)
    @Modifying //modifying query
    Optional<UserEntity> deleteUserEntityByPublicId(UUID publicId);

    boolean existsByEmail(String email);

    Optional<UserEntity> findUserEntityByEmail(String email);
}

package com.example.demo.user;

import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserDetailsResponseModel createOrUpdateUser(UUID publicId, UserDetailsRequestModel userDetailsRequestModel);
    UserDetailsResponseModel partialUpdateUser(UUID publicId, UserDetailsRequestModel userDetailsRequestModel);
    Iterable<UserDetailsResponseModel> getUsers();
    UserDetailsResponseModel getUser(UUID publicId);
    void deleteUser(UUID publicId);
    public boolean userExistsByUUID(UUID uuid);
}

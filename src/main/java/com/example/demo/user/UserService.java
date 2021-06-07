package com.example.demo.user;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDetailsResponseModel createOrUpdateUser(UUID publicId, UserDetailsRequestModel userDetailsRequestModel);
    UserDetailsResponseModel partialUpdateUser(UUID publicId, UserDetailsRequestModel userDetailsRequestModel);
    Iterable<UserDetailsResponseModel> getUsers();
    UserDetailsResponseModel getUser(UUID publicId);
    void deleteUser(UUID publicId);
}

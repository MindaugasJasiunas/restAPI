package com.example.demo.user;

import com.example.demo.converters.UserEntityUserRequestMapper;
import com.example.demo.converters.UserEntityUserResponseMapper;
import com.example.demo.error.UserCreationException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.UserDetailsRequestModel;
import com.example.demo.model.UserDetailsResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired  // optional
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<UserDetailsResponseModel> getUsers(){
        Iterable<UserEntity> users= userRepository.findAll();
        Iterable<UserDetailsResponseModel> userDetailsResponseModels= UserEntityUserResponseMapper.INSTANCE.userEntitiesToUserDetailsResponseModels(users);
        return userDetailsResponseModels;
    }

    @Override
    public UserDetailsResponseModel getUser(UUID publicId){
        UserEntity user= userRepository.findUserEntityByPublicId(publicId).orElseThrow(() -> new UserNotFoundException("User with public ID " + publicId + " not found!"));
        return UserEntityUserResponseMapper.INSTANCE.userEntityToUserDetailsResponseModel(user);
    }


    @Override
    public UserDetailsResponseModel createOrUpdateUser(UUID publicId, UserDetailsRequestModel userDetailsRequestModel){
        UserEntity userEntity= UserEntityUserRequestMapper.INSTANCE.userDetailsRequestModelToUserEntity(userDetailsRequestModel);

        // UPDATE
        if(publicId!=null){
            if(userRepository.findUserEntityByPublicId(publicId).isPresent()){
                UserEntity user=userRepository.findUserEntityByPublicId(publicId).get();

                userEntity.setId(user.getId());

                userEntity.setPublicId(publicId);
            }
        }
        try{
            UserEntity userSaved= userRepository.save(userEntity);
            return UserEntityUserResponseMapper.INSTANCE.userEntityToUserDetailsResponseModel(userSaved);
        } catch (DataIntegrityViolationException emailExistsException) {
            throw new UserCreationException("DB Error occurred when updating User.");
        }

    }


    @Override
    public UserDetailsResponseModel partialUpdateUser(UUID publicId, UserDetailsRequestModel userDetailsRequestModel){
        UserEntity userFromDB= userRepository.findUserEntityByPublicId(publicId).orElseThrow(() -> new UserNotFoundException("User with public ID "+publicId+" doesn't exist!"));
        if(userDetailsRequestModel.getFirstName()!=null){
            userFromDB.setFirstName(userDetailsRequestModel.getFirstName());
        }
        if(userDetailsRequestModel.getLastName()!=null){
            userFromDB.setLastName(userDetailsRequestModel.getLastName());
        }
        if(userDetailsRequestModel.getEmail()!=null){
            userFromDB.setEmail(userDetailsRequestModel.getEmail());
        }
        if(userDetailsRequestModel.getPassword()!=null){
            userFromDB.setEncryptedPassword(userDetailsRequestModel.getPassword());
        }
        userFromDB= userRepository.save(userFromDB);
        return UserEntityUserResponseMapper.INSTANCE.userEntityToUserDetailsResponseModel(userFromDB);
    }

    @Transactional
    @Override
    public void deleteUser(UUID publicId) {
        userRepository.deleteUserEntityByPublicId(publicId);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userFromDB= userRepository.findUserEntityByEmail(email).get();
        return new User(userFromDB.getEmail(), userFromDB.getEncryptedPassword(), new ArrayList<>());  // empty list for authorities
    }
}

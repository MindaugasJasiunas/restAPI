package com.example.demo.converters;

import com.example.demo.user.UserDetailsResponseModel;
import com.example.demo.user.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityUserResponseMapper {
    UserEntityUserResponseMapper INSTANCE= Mappers.getMapper(UserEntityUserResponseMapper.class);

    @Mappings({  //if there is no field in target class - doesn't matter
    })
    UserDetailsResponseModel userEntityToUserDetailsResponseModel(UserEntity userEntity);

    @Mappings({
            @Mapping(target = "id", ignore = true), //UserEntity has id field that we need to ignore
            @Mapping(target = "version", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastModifiedAt", ignore = true),  
            @Mapping(target = "encryptedPassword", ignore = true),  
            @Mapping(target = "emailVerificationToken", ignore = true),  
            @Mapping(target = "emailVerificationStatus", ignore = true)  
    })
    UserEntity userDetailsResponseModelToUserEntity(UserDetailsResponseModel userDetailsResponseModel);


    Iterable<UserDetailsResponseModel> userEntitiesToUserDetailsResponseModels(Iterable<UserEntity> userEntity);
    Iterable<UserEntity> userDetailsResponseModelsToUserEntities(Iterable<UserDetailsResponseModel> userDetailsResponseModel);
}

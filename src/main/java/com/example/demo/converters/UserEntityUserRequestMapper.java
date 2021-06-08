package com.example.demo.converters;

import com.example.demo.user.UserDetailsRequestModel;
import com.example.demo.user.UserDetailsResponseModel;
import com.example.demo.user.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityUserRequestMapper {
    UserEntityUserRequestMapper INSTANCE= Mappers.getMapper(UserEntityUserRequestMapper.class);

    @Mappings({@Mapping(target = "password", source = "encryptedPassword")})
    UserDetailsRequestModel userEntityToUserDetailsRequestModel(UserEntity userEntity);

    @Mappings({
            @Mapping(target = "id", ignore = true), //UserEntity has id field that we need to ignore
            @Mapping(target = "publicId", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastModifiedAt", ignore = true),
            @Mapping(target = "emailVerificationToken", ignore = true),
            @Mapping(target = "emailVerificationStatus", ignore = true),
            @Mapping(source = "password", target = "encryptedPassword")
    })
    UserEntity userDetailsRequestModelToUserEntity(UserDetailsRequestModel userDetailsRequestModel);


    Iterable<UserDetailsRequestModel> userEntitiesToUserDetailsRequestModels(Iterable<UserEntity> userEntity);
    Iterable<UserEntity> userDetailsRequestModelsToUserEntities(Iterable<UserDetailsRequestModel> userDetailsRequestModel);
}

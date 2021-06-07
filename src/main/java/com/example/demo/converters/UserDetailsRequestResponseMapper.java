package com.example.demo.converters;

import com.example.demo.user.UserDetailsRequestModel;
import com.example.demo.user.UserDetailsResponseModel;
import com.example.demo.user.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserDetailsRequestResponseMapper {
    UserDetailsRequestResponseMapper INSTANCE= Mappers.getMapper(UserDetailsRequestResponseMapper.class);

    @Mappings({  //if there is no field in target class - doesn't matter
            @Mapping(target = "publicId", ignore = true)})
    UserDetailsResponseModel userDetailsRequestModelToUserDetailsResponseModel(UserDetailsRequestModel userDetailsRequestModel);

    @Mappings({@Mapping(target = "password", ignore = true)})
    UserDetailsRequestModel userDetailsResponseModelToUserDetailsRequestModel(UserDetailsResponseModel userDetailsResponseModel);


    Iterable<UserDetailsResponseModel> userDetailsRequestModelsToUserDetailsResponseModels(Iterable<UserDetailsRequestModel> userDetailsRequestModel);
    Iterable<UserDetailsRequestModel> userDetailsResponseModelsToUserDetailsRequestModels(Iterable<UserDetailsResponseModel> userDetailsResponseModel);
}

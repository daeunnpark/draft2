package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.model.User;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    UserDto fromUser(User user, @Context CycleAvoidingMappingContext context);

}
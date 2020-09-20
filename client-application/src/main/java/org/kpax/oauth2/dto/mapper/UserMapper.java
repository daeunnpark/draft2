package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setImage(user.getImage());
    }
}

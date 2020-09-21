package org.kpax.oauth2.service.user;

import org.kpax.oauth2.dto.model.UserDto;

import java.util.List;

public interface IUserService {
    UserDto findById(Long userId);

    List<UserDto> findByChatId(Long chatId);

    List<UserDto> getAll();
}

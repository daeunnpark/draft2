package org.kpax.oauth2.service.user;

import org.kpax.oauth2.model.User;

import java.util.List;

public interface IUserService {
    List<User> findByChatId(Long chatId);
    List<User> getAll();
}

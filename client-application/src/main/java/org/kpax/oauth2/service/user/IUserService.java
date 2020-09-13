package org.kpax.oauth2.service.user;

import org.kpax.oauth2.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long userId);
    List<User> findByChatId(Long chatId);
    List<User> getAll();
}

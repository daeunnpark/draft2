package org.kpax.oauth2.service.user;

import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.UserRepository;
import org.kpax.oauth2.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findByChatId(Long chatId) {
        return userRepository.findByChatsId(chatId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}

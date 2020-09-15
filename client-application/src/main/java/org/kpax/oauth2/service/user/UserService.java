package org.kpax.oauth2.service.user;

import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.UserRepository;
import org.kpax.oauth2.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findByChatId(Long chatId) {
        return userRepository.findByChatsId(chatId);
    }

    @Override
    public List<User> getAll() {
        System.out.println("GET ALL");
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(User::getName));
        return users;
    }
}

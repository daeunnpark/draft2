package org.kpax.oauth2.service.user;

import org.kpax.oauth2.dto.mapper.UserMapper;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.exception.ResourceNotFoundException;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CycleAvoidingMappingContext context;

    @Override
    public UserDto findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        System.out.println(UserMapper.MAPPER.fromUser(user, context));
        return UserMapper.MAPPER.fromUser(user, context);
    }

    @Override
    public List<UserDto> findByChatId(Long chatId) {
        return userRepository.findByChatsId(chatId)
                .stream().map(user -> UserMapper.MAPPER.fromUser(user, context))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> users = userRepository.findAll()
                .stream().map(user -> UserMapper.MAPPER.fromUser(user, context))
                .collect(Collectors.toList());

        users.sort(Comparator.comparing(UserDto::getName));
        return users;
    }


}

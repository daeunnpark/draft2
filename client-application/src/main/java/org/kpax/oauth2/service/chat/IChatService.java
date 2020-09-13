package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.model.Chat;

import java.util.List;
import java.util.Optional;

public interface IChatService {
    List<Chat> findByUserId(Long userId);
    //Optional<Chat> findByUserIdAndChatId(Long userId, Long chatId);
    void deleteById(Long chatId);
    Chat save(Chat chat);
}


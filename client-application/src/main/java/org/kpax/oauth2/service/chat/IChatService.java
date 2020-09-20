package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;

import java.util.List;
import java.util.Optional;

public interface IChatService {
    List<Chat> findByUserId(Long userId);
    //Optional<Chat> findByUserIdAndChatId(Long userId, Long chatId);
    void deleteById(Long chatId);
    Chat findById(Long chatId);
    void sentPublicMessage(Message message);
    void sentPrivateMessage(Message message);
    List<Chat> findAll();
    Chat save(Chat chat);
}


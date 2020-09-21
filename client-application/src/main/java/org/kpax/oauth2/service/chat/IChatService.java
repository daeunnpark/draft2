package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.dto.model.MessageDto;

import java.util.List;

public interface IChatService {
    ChatDto findById(Long chatId);

    List<ChatDto> findByUserId(Long userId);

    void deleteById(Long chatId);

    void sentPublicMessage(MessageDto messageDto);

    ChatDto saveChat(ChatDto chatDto);
    //List<ChatDto> findAll();
}


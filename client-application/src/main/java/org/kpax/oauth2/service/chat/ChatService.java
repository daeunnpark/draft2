package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService implements IChatService {
    @Autowired
    ChatRepository chatRepository;

    @Override
    public List<Chat> findByUserId(Long userId) {
        return chatRepository.findByMembersId(userId);
    }

    @Override
    public void deleteById(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    public Chat findById(Long chatId){
        return chatRepository.findById(chatId).get();
    }

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

}

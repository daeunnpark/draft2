package org.kpax.oauth2.service.message;

import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.repository.MessageRepository;
import org.kpax.oauth2.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> findRecentMsg(Long chatId) {
        return messageRepository.findTop20ByChatIdOrderBySentAtDesc(chatId);
    }
}

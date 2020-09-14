package org.kpax.oauth2.service.message;

import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.repository.MessageRepository;
import org.kpax.oauth2.service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MessageService implements IMessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> findRecentMsg(Long chatId) {
        List<Message> msgs =messageRepository.findTop20ByChatIdOrderBySentAtDesc(chatId);
        Collections.reverse(msgs);
        return msgs;
    }
}

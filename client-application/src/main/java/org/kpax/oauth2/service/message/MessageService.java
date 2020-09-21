package org.kpax.oauth2.service.message;

import org.kpax.oauth2.dto.mapper.MessageMapper;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.exception.ResourceNotFoundException;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.repository.ChatRepository;
import org.kpax.oauth2.repository.MessageRepository;
import org.kpax.oauth2.repository.UserRepository;
import org.kpax.oauth2.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService implements IMessageService {

    @Autowired
    CycleAvoidingMappingContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Override
    public MessageDto findById(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "messageId", messageId));
        return MessageMapper.MAPPER.fromMessage(message, context);
        // return MessageMapper.toMessageDto(message);
    }

    @Override
    public List<MessageDto> findRecentMsg(Long chatId) {
        List<MessageDto> msgs = messageRepository.findTop20ByChatIdOrderBySentAtDesc(chatId)
                .stream().map(message -> MessageMapper.MAPPER.fromMessage(message, context))
                .collect(Collectors.toList());
        Collections.reverse(msgs);
        return msgs;
    }

    @Override
    public void addMessage(MessageDto messageDto) {
        saveMessage(messageDto);
    }

    @Override
    public void saveMessage(MessageDto messageDto) {
        /*
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setType(Message.MessageType.TEXT);
        message.setChat(chatService.getChat(messageDto.getChatId()));
        message.setUser(userRepository.findById(messageDto.getUserId()).get());
        message.setContent(messageDto.getContent());
        message.setSentAt(messageDto.getSentAt());
        message.setUnreadCnt(messageDto.getUnreadCnt());
        */
        Message message = MessageMapper.MAPPER.toMessage(messageDto, context);
        messageRepository.save(message);
    }
}

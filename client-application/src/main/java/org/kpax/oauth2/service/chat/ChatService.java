package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.dto.mapper.ChatMapper;
import org.kpax.oauth2.dto.mapper.MessageMapper;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.exception.ResourceNotFoundException;
import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.ChatRepository;
import org.kpax.oauth2.repository.MessageRepository;
import org.kpax.oauth2.repository.UserRepository;
import org.kpax.oauth2.service.user.UserService;
import org.kpax.oauth2.util.Destinations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService implements IChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CycleAvoidingMappingContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate webSocketMessagingTemplate;

    @Autowired
    private UserService userService;

    @Override
    public ChatDto findById(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "chatId", chatId));
        return ChatMapper.MAPPER.fromChat(chat, context);
    }

    @Override
    public List<ChatDto> findByUserId(Long userId) {
        return chatRepository.findByMembersId(userId)
                .stream().map(Chat -> ChatMapper.MAPPER.fromChat(Chat, context))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    @Override
    public void sendPublicMessage(MessageDto messageDto) {
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.MessagesInList(messageDto.getUserId()),
                messageDto);
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.MessagesInRoom(messageDto.getUserId()),
                messageDto);

        Message message = MessageMapper.MAPPER.toMessage(messageDto, context);
        messageRepository.save(message);
    }

    public List<Long> exit(Long userId, List<Integer> chatIds) {
        List<Long> exitChatIds = new ArrayList<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        for (Integer chatId : chatIds) {
            Long longChatId = chatId.longValue();
            Chat chat = chatRepository.findById(longChatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Chat", "chatId", longChatId));
            chat.getMembers().remove(user);
            exitChatIds.add(longChatId);
        }
        return exitChatIds;
    }

    public ChatDto create(Long userId, ChatDto chatDto) {
        chatDto.setMembers(chatDto.getFriendIds()
                .stream().map(id -> userService.findById(id))
                .collect(Collectors.toList()));
        chatDto.getMembers().add(userService.findById(userId));

        if (chatDto.getMembers().size() == 1) {
            chatDto.setType("SELF");
        } else if (chatDto.getMembers().size() > 2) {
            chatDto.setType("GROUP");
        }
        chatDto.setLastAt(new Date());
        return saveChat(chatDto);
    }


    @Override
    public ChatDto saveChat(ChatDto chatDto) {
        Chat chat = ChatMapper.MAPPER.toChat(chatDto, context);
        addChatToMembers(chat);
        return ChatMapper.MAPPER.fromChat(chatRepository.save(chat), context);
    }

    public void addChatToMembers(Chat chat) {
        for (User member : chat.getMembers()) {
            member.getChats().add(chat);
        }
    }
}



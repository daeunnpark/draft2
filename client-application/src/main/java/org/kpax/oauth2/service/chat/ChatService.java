package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.dto.mapper.ChatMapper;
import org.kpax.oauth2.dto.mapper.MessageMapper;
import org.kpax.oauth2.dto.mapper.UserMapper;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.exception.ResourceNotFoundException;
import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.ChatRepository;
import org.kpax.oauth2.repository.MessageRepository;
import org.kpax.oauth2.repository.UserRepository;
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
    public void sentPublicMessage(MessageDto messageDto) {
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.MessagesInList(messageDto.getUser().getId()),
                messageDto);
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.MessagesInRoom(messageDto.getUser().getId()),
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
        chatDto.getFriendIds().add(userId);
        List<UserDto> members = new ArrayList<>();

        for (Long id : chatDto.getFriendIds()) {
            User friend = userRepository.findById(userId).get();
            friend.getChats().add(ChatMapper.MAPPER.toChat(chatDto, context));
            members.add(UserMapper.MAPPER.fromUser(friend, context));
        }

        chatDto.setMembers(members);

        if (members.size() == 1) {
            chatDto.setType(Chat.ChatType.SELF.toString());
        } else if (members.size() > 2) {
            chatDto.setType(Chat.ChatType.GROUP.toString());
        }
        chatDto.setLastAt(new Date());
        return saveChat(chatDto);
    }


    @Override
    public ChatDto saveChat(ChatDto chatDto) {
        Chat chat = ChatMapper.MAPPER.toChat(chatDto, context);
        return ChatMapper.MAPPER.fromChat(chatRepository.save(chat), context);
    }
}



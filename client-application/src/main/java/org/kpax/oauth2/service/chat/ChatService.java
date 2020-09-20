package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.ChatRepository;
import org.kpax.oauth2.repository.MessageRepository;
import org.kpax.oauth2.service.user.UserService;
import org.kpax.oauth2.util.Destinations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChatService implements IChatService{
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate webSocketMessagingTemplate;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Chat> findByUserId(Long userId) {
        return chatRepository.findByMembersId(userId);
    }

    @Override
    public void deleteById(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    @Override
    public Chat findById(Long chatId) {
        return chatRepository.findById(chatId).get();
    }

    public List<Long> exit(Long userId, List<Integer> chatIds){
        System.out.println(chatIds);
        List<Long> exitChatIds = new ArrayList<>();
        User user = userService.findById(userId).get();

        for(Integer chatId : chatIds){
            Long longChatId = chatId.longValue();
            chatRepository.findById(longChatId).get().getMembers().remove(user);
            exitChatIds.add(longChatId);
        }
        return exitChatIds;
    }

    @Override
    public void sentPublicMessage(Message message) {
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.MessagesInList(message.getUserId()),
                message);
        webSocketMessagingTemplate.convertAndSend(
                Destinations.ChatRoom.MessagesInRoom(message.getChat().getId()),
                message);

        messageRepository.save(message);
    }


    @Override
    public void sentPrivateMessage(Message message) {
        /*
        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getToUser(),
                Destinations.ChatRoom.privateMessages(message.getChat().getId()),
                message);

        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getFromUser(),
                Destinations.ChatRoom.privateMessages(message.getChat().getId()),
                message);


        messageRepository.save(message);
        */
    }


    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat create(Long userId, Chat chat){
        chat.getFriendIds().add(userId);

        List<User> members = new ArrayList<>();

        for(Long id : chat.getFriendIds()){
            User friend = userService.findById(id).get();
            friend.getChats().add(chat);
            members.add(friend);
        }
        chat.setMembers(members);

        if(members.size()==1) {
            chat.setType(Chat.ChatType.SELF);
        }else if(members.size()>2){
            chat.setType(Chat.ChatType.GROUP);
        }
        chat.setLastAt(new Date());

        return chatRepository.save(chat);
    }

    /*
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

     */

}

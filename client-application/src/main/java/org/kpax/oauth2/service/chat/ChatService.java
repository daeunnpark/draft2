package org.kpax.oauth2.service.chat;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.ChatRepository;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ChatService implements IChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Chat> findByUserId(Long userId) {
        return chatRepository.findByMembersId(userId);
    }

    @Override
    public void deleteById(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    public void exit(Long userId, Long chatId){
        User user = userService.findById(userId).get();
        chatRepository.findById(chatId).get().getMembers().remove(user);
    }

    public Chat findById(Long chatId){
        return chatRepository.findById(chatId).get();
    }

    public Chat create(Long userId, Chat chat){
        chat.getFriendIds().add(userId);
        Set<User> members = new HashSet<>();

        for(Long id : chat.getFriendIds()){
            User friend = userService.findById(id).get();
            friend.getChats().add(chat);
            members.add(friend);
        }
        chat.setMembers(members);
        if(members.size()>2){
            chat.setType(Chat.ChatType.GROUP);
        }

        return chatRepository.save(chat);
    }

    /*
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

     */

}

package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.model.Chat;

import java.util.stream.Collectors;

public class ChatMapper {
    public static ChatDto toChatDto(Chat chat) {
        return new ChatDto()
                .setId(chat.getId())
                .setType(chat.getType().toString())
                .setName(chat.getName())
                .setMembers(chat.getMembers().stream().map(User -> new UserDto()).collect(Collectors.toList()))
                .setFriendIds(chat.getFriendIds())
                .setLastMessageId(chat.getLastMessage().getId())
                .setLastAt(chat.getLastAt())
                .setImage(chat.getImage())
                .setUnreadCnt(chat.getUnreadCnt());
    }
}

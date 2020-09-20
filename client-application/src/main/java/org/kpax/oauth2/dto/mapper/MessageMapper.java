package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.User;

public class MessageMapper {
    public static MessageDto toMessageDto(Message message) {
        return new MessageDto()
                .setId(message.getId())
                .setType(message.getType().toString())
                .setChatId(message.getChat().getId())
                .setUserId(message.getUserId())
                .setContent(message.getContent())
                .setSentAt(message.getSentAt())
                .setUnreadCnt(message.getUnreadCnt());
    }
}


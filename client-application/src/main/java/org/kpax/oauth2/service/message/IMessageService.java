package org.kpax.oauth2.service.message;

import org.kpax.oauth2.dto.model.MessageDto;

import java.util.List;

public interface IMessageService {
    MessageDto findById(Long messageId);

    List<MessageDto> findRecentMsg(Long chatId);

    void addMessage(MessageDto messageDto);

    void saveMessage(MessageDto messageDto);
}

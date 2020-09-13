package org.kpax.oauth2.service.message;

import org.kpax.oauth2.model.Message;

import java.util.List;

public interface IMessageService {
    List<Message> findRecentMsg(Long chatId);
}

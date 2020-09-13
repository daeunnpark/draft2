package org.kpax.oauth2.repository;

import org.kpax.oauth2.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findTop20ByChatIdOrderBySentAtDesc(Long chatId);
}

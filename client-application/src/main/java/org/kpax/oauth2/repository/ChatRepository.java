package org.kpax.oauth2.repository;

import org.kpax.oauth2.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByMembersId(Long userId);
    //Optional<Chat> findByUChatId(Long userId, Long chatId);
    //void deleteByChatIdAndMembersId(Long chatId, Long userId);
    void deleteById(Long chatId);
}

package org.kpax.oauth2.repository;

import org.kpax.oauth2.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    //Chat findById(Long chatId);

    List<Chat> findByMembersId(Long userId);

    void deleteById(Long chatId);
}



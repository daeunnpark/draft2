package org.kpax.oauth2.service.relationship;

import org.kpax.oauth2.model.Relationship;
import org.kpax.oauth2.model.User;

import java.util.List;

public interface IRelationshipService {
    List<User> findFriendsByUserId(String relation, Long userId);
    void deleteFriend(Long userId, Long friendId);
    void save(Relationship relation);
}

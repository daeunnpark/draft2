package org.kpax.oauth2.service.relationship;

import org.kpax.oauth2.dto.model.RelationshipDto;
import org.kpax.oauth2.dto.model.UserDto;

import java.util.List;

public interface IRelationshipService {
    RelationshipDto findById(Long relationshipId);

    List<UserDto> findFriendsByUserId(Long userId);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

}

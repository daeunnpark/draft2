package org.kpax.oauth2.service.relationship;

import org.kpax.oauth2.model.Relationship;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.RelationshipRepository;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService implements IRelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<User> findFriendsByUserId(Long userId) {
        return relationshipRepository.findByStatusLikeAndUser1IdOrUser2Id("FRIEND", userId, userId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        long user1Id = Math.min(userId, friendId);
        long user2Id = user1Id==userId ? friendId : userId;
        Relationship friendship = new Relationship(userService.findById(user1Id).get(),  userService.findById(user2Id).get(), Relationship.RelationshipType.FRIEND);
        relationshipRepository.save(friendship);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        long user1Id = Math.min(userId, friendId);
        long user2Id = user1Id==userId ? friendId : userId;
        relationshipRepository.deleteByUser1IdAndUser2IdAndStatusLike(user1Id, user2Id, "FRIEND");
    }

    @Override
    public void save(Relationship relation) {
        relationshipRepository.save(relation);
    }

}

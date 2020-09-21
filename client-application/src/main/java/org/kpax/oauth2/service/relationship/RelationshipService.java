package org.kpax.oauth2.service.relationship;

import org.kpax.oauth2.dto.mapper.RelationshipMapper;
import org.kpax.oauth2.dto.mapper.UserMapper;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.RelationshipDto;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.exception.ResourceNotFoundException;
import org.kpax.oauth2.model.Relationship;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.RelationshipRepository;
import org.kpax.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class RelationshipService implements IRelationshipService {
    @Autowired
    RelationshipRepository relationshipRepository;
    @Autowired
    CycleAvoidingMappingContext context;
    @Autowired
    private UserRepository userRepository;

    @Override
    public RelationshipDto findById(Long relationshipId) {
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "relationshipId", relationshipId));
        return RelationshipMapper.MAPPER.fromRelationship(relationship, context);
    }

    @Override
    public List<UserDto> findFriendsByUserId(Long userId) {
        List<Relationship> friendships = relationshipRepository
                .findByStatusLikeAndUser1IdOrUser2Id(Relationship.RelationshipType.FRIEND, userId, userId);
        List<UserDto> friends = new ArrayList<>();

        for (Relationship friendship : friendships) {
            User friend = friendship.getUser1().getId() == userId ? friendship.getUser2() : friendship.getUser1();
            friends.add(UserMapper.MAPPER.fromUser(friend, context));

        }
        friends.sort(Comparator.comparing(UserDto::getName));
        return friends;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        long user1Id = Math.min(userId, friendId);
        long user2Id = user1Id == userId ? friendId : userId;

        RelationshipDto friendshipDto = new RelationshipDto()
                .setUser1Id(user1Id)
                .setUser2Id(user2Id)
                .setStatus(Relationship.RelationshipType.FRIEND.toString());

        save(friendshipDto);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        long user1Id = Math.min(userId, friendId);
        long user2Id = user1Id == userId ? friendId : userId;
        relationshipRepository.deleteByUser1IdAndUser2IdAndStatusLike(user1Id, user2Id, Relationship.RelationshipType.FRIEND);
    }

    public void save(RelationshipDto friendshipDto) {
        User user1 = userRepository.findById(friendshipDto.getUser1Id())
                .orElseThrow(() -> new ResourceNotFoundException("User", "user1Id", friendshipDto.getUser1Id()));

        User user2 = userRepository.findById(friendshipDto.getUser2Id())
                .orElseThrow(() -> new ResourceNotFoundException("User", "user2Id", friendshipDto.getUser2Id()));

        Relationship friendship = new Relationship()
                .setUser1(user1)
                .setUser2(user2)
                .setStatus(Relationship.RelationshipType.valueOf(friendshipDto.getStatus()));

        relationshipRepository.save(friendship);
    }


}

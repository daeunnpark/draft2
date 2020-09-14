package org.kpax.oauth2.repository;

import org.kpax.oauth2.model.Relationship;
import org.kpax.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Relation;
import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> findByStatusLikeAndUser1IdOrUser2Id(Relationship.RelationshipType status, Long user1Id, Long user2Id);
    void deleteByUser1IdAndUser2IdAndStatusLike(Long user1Id, Long user2Id, Relationship.RelationshipType status);

}

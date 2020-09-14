package org.kpax.oauth2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Relation;
import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Relationship {

    public enum RelationshipType{
        NONE,
        FRIEND,
        BLOCKED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user1;
    @OneToOne
    private User user2;
    @Enumerated(EnumType.STRING)
    private RelationshipType status;

    public Relationship(User user1, User user2, RelationshipType status){
        this.user1=user1;
        this.user2=user2;
        this.status=status;
    }
}

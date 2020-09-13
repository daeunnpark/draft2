package org.kpax.oauth2.model;

import javax.persistence.*;

enum RelationshipType{
    NONE,
    FRIEND,
    BLOCKED
}
@Entity
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user1;
    @OneToOne
    private User user2;
    @Enumerated(EnumType.STRING)
    private RelationshipType status;
}

package org.kpax.oauth2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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

    public enum RelationshipType {
        NONE,
        FRIEND,
        BLOCKED
    }
}

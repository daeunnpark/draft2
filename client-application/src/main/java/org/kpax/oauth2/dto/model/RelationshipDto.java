package org.kpax.oauth2.dto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kpax.oauth2.model.Relationship;
import org.kpax.oauth2.model.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Getter
@Setter
@Accessors(chain = true)
public class RelationshipDto {
    private Long id;
    private Long user1Id;
    private Long user2Id;
    private String status;
}

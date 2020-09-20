package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.model.RelationshipDto;
import org.kpax.oauth2.model.Relationship;

public class RelationshipMapper {
    public static RelationshipDto toUserDto(Relationship relationship) {
        return new RelationshipDto()
                .setId(relationship.getId())
                .setUser1Id(relationship.getUser1().getId())
                .setUser2Id(relationship.getUser2().getId())
                .setStatus(relationship.getStatus().toString());
    }
}

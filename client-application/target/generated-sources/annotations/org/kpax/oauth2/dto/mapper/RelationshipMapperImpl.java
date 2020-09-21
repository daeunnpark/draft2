package org.kpax.oauth2.dto.mapper;

import javax.annotation.Generated;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.RelationshipDto;
import org.kpax.oauth2.model.Relationship;
import org.kpax.oauth2.model.Relationship.RelationshipType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-21T22:26:09+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class RelationshipMapperImpl implements RelationshipMapper {

    @Override
    public Relationship toRelationship(RelationshipDto relationshipDto, CycleAvoidingMappingContext context) {
        Relationship target = context.getMappedInstance( relationshipDto, Relationship.class );
        if ( target != null ) {
            return target;
        }

        if ( relationshipDto == null ) {
            return null;
        }

        Relationship relationship = new Relationship();

        context.storeMappedInstance( relationshipDto, relationship );

        relationship.setId( relationshipDto.getId() );
        if ( relationshipDto.getStatus() != null ) {
            relationship.setStatus( Enum.valueOf( RelationshipType.class, relationshipDto.getStatus() ) );
        }

        return relationship;
    }

    @Override
    public RelationshipDto fromRelationship(Relationship relationship, CycleAvoidingMappingContext context) {
        RelationshipDto target = context.getMappedInstance( relationship, RelationshipDto.class );
        if ( target != null ) {
            return target;
        }

        if ( relationship == null ) {
            return null;
        }

        RelationshipDto relationshipDto = new RelationshipDto();

        context.storeMappedInstance( relationship, relationshipDto );

        relationshipDto.setId( relationship.getId() );
        if ( relationship.getStatus() != null ) {
            relationshipDto.setStatus( relationship.getStatus().name() );
        }

        return relationshipDto;
    }
}

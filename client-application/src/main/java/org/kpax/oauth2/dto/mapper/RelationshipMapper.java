package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.RelationshipDto;
import org.kpax.oauth2.model.Relationship;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RelationshipMapper {

    RelationshipMapper MAPPER = Mappers.getMapper(RelationshipMapper.class);

    Relationship toRelationship(RelationshipDto relationshipDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    RelationshipDto fromRelationship(Relationship relationship, @Context CycleAvoidingMappingContext context);

}

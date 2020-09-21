package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.MediaDto;
import org.kpax.oauth2.model.Media;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MediaMapper {

    MediaMapper MAPPER = Mappers.getMapper(MediaMapper.class);

    Media toMedia(MediaDto mediaDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    MediaDto fromMedia(Media media, @Context CycleAvoidingMappingContext context);

}

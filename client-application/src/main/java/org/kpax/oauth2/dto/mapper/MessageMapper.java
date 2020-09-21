package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.model.Message;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    Message toMessage(MessageDto messageDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    MessageDto fromMessage(Message message, @Context CycleAvoidingMappingContext context);

}

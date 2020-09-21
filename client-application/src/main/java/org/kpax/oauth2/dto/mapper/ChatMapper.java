package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.model.Chat;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {

    ChatMapper MAPPER = Mappers.getMapper(ChatMapper.class);

    Chat toChat(ChatDto chatDto, @Context CycleAvoidingMappingContext context);

    @InheritInverseConfiguration
    ChatDto fromChat(Chat chat, @Context CycleAvoidingMappingContext context);
}
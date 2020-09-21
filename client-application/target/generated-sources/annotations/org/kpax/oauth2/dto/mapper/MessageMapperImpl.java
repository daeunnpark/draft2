package org.kpax.oauth2.dto.mapper;

import javax.annotation.Generated;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.Message.MessageType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-21T22:26:09+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class MessageMapperImpl implements MessageMapper {

    @Override
    public Message toMessage(MessageDto messageDto, CycleAvoidingMappingContext context) {
        Message target = context.getMappedInstance( messageDto, Message.class );
        if ( target != null ) {
            return target;
        }

        if ( messageDto == null ) {
            return null;
        }

        Message message = new Message();

        context.storeMappedInstance( messageDto, message );

        message.setId( messageDto.getId() );
        if ( messageDto.getType() != null ) {
            message.setType( Enum.valueOf( MessageType.class, messageDto.getType() ) );
        }
        message.setContent( messageDto.getContent() );
        message.setSentAt( messageDto.getSentAt() );
        message.setUnreadCnt( messageDto.getUnreadCnt() );

        return message;
    }

    @Override
    public MessageDto fromMessage(Message message, CycleAvoidingMappingContext context) {
        MessageDto target = context.getMappedInstance( message, MessageDto.class );
        if ( target != null ) {
            return target;
        }

        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        context.storeMappedInstance( message, messageDto );

        messageDto.setId( message.getId() );
        if ( message.getType() != null ) {
            messageDto.setType( message.getType().name() );
        }
        messageDto.setContent( message.getContent() );
        messageDto.setSentAt( message.getSentAt() );
        messageDto.setUnreadCnt( message.getUnreadCnt() );

        return messageDto;
    }
}

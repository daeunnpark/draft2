package org.kpax.oauth2.dto.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Chat.ChatType;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.Message.MessageType;
import org.kpax.oauth2.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-22T16:56:34+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDto userDto, CycleAvoidingMappingContext context) {
        User target = context.getMappedInstance( userDto, User.class );
        if ( target != null ) {
            return target;
        }

        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        context.storeMappedInstance( userDto, user );

        user.setId( userDto.getId() );
        user.setName( userDto.getName() );
        user.setImage( userDto.getImage() );
        user.setChats( chatDtoListToChatList( userDto.getChats(), context ) );

        return user;
    }

    @Override
    public UserDto fromUser(User user, CycleAvoidingMappingContext context) {
        UserDto target = context.getMappedInstance( user, UserDto.class );
        if ( target != null ) {
            return target;
        }

        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        context.storeMappedInstance( user, userDto );

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        userDto.setImage( user.getImage() );
        userDto.setChats( chatListToChatDtoList( user.getChats(), context ) );

        return userDto;
    }

    protected List<User> userDtoListToUserList(List<UserDto> list, CycleAvoidingMappingContext context) {
        List<User> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<User> list1 = new ArrayList<User>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( UserDto userDto : list ) {
            list1.add( toUser( userDto, context ) );
        }

        return list1;
    }

    protected Message messageDtoToMessage(MessageDto messageDto, CycleAvoidingMappingContext context) {
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
        message.setChat( chatDtoToChat( messageDto.getChat(), context ) );
        message.setUserId( messageDto.getUserId() );
        message.setContent( messageDto.getContent() );
        message.setSentAt( messageDto.getSentAt() );
        message.setUnreadCnt( messageDto.getUnreadCnt() );

        return message;
    }

    protected Chat chatDtoToChat(ChatDto chatDto, CycleAvoidingMappingContext context) {
        Chat target = context.getMappedInstance( chatDto, Chat.class );
        if ( target != null ) {
            return target;
        }

        if ( chatDto == null ) {
            return null;
        }

        Chat chat = new Chat();

        context.storeMappedInstance( chatDto, chat );

        chat.setId( chatDto.getId() );
        if ( chatDto.getType() != null ) {
            chat.setType( Enum.valueOf( ChatType.class, chatDto.getType() ) );
        }
        chat.setName( chatDto.getName() );
        chat.setMembers( userDtoListToUserList( chatDto.getMembers(), context ) );
        Set<Long> set = chatDto.getFriendIds();
        if ( set != null ) {
            chat.setFriendIds( new HashSet<Long>( set ) );
        }
        chat.setLastMessage( messageDtoToMessage( chatDto.getLastMessage(), context ) );
        chat.setLastAt( chatDto.getLastAt() );
        chat.setImage( chatDto.getImage() );
        chat.setUnreadCnt( chatDto.getUnreadCnt() );

        return chat;
    }

    protected List<Chat> chatDtoListToChatList(List<ChatDto> list, CycleAvoidingMappingContext context) {
        List<Chat> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Chat> list1 = new ArrayList<Chat>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( ChatDto chatDto : list ) {
            list1.add( chatDtoToChat( chatDto, context ) );
        }

        return list1;
    }

    protected List<UserDto> userListToUserDtoList(List<User> list, CycleAvoidingMappingContext context) {
        List<UserDto> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<UserDto> list1 = new ArrayList<UserDto>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( User user : list ) {
            list1.add( fromUser( user, context ) );
        }

        return list1;
    }

    protected MessageDto messageToMessageDto(Message message, CycleAvoidingMappingContext context) {
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
        messageDto.setChat( chatToChatDto( message.getChat(), context ) );
        messageDto.setUserId( message.getUserId() );
        messageDto.setContent( message.getContent() );
        messageDto.setSentAt( message.getSentAt() );
        messageDto.setUnreadCnt( message.getUnreadCnt() );

        return messageDto;
    }

    protected ChatDto chatToChatDto(Chat chat, CycleAvoidingMappingContext context) {
        ChatDto target = context.getMappedInstance( chat, ChatDto.class );
        if ( target != null ) {
            return target;
        }

        if ( chat == null ) {
            return null;
        }

        ChatDto chatDto = new ChatDto();

        context.storeMappedInstance( chat, chatDto );

        chatDto.setId( chat.getId() );
        if ( chat.getType() != null ) {
            chatDto.setType( chat.getType().name() );
        }
        chatDto.setName( chat.getName() );
        chatDto.setMembers( userListToUserDtoList( chat.getMembers(), context ) );
        Set<Long> set = chat.getFriendIds();
        if ( set != null ) {
            chatDto.setFriendIds( new HashSet<Long>( set ) );
        }
        chatDto.setLastMessage( messageToMessageDto( chat.getLastMessage(), context ) );
        chatDto.setLastAt( chat.getLastAt() );
        chatDto.setImage( chat.getImage() );
        chatDto.setUnreadCnt( chat.getUnreadCnt() );

        return chatDto;
    }

    protected List<ChatDto> chatListToChatDtoList(List<Chat> list, CycleAvoidingMappingContext context) {
        List<ChatDto> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<ChatDto> list1 = new ArrayList<ChatDto>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Chat chat : list ) {
            list1.add( chatToChatDto( chat, context ) );
        }

        return list1;
    }
}

package org.kpax.oauth2.controller;


import org.kpax.oauth2.dto.model.MessageDto;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat/message")
    @SendTo("/sub/public")
    public MessageDto sendMessage(@Payload MessageDto messageDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
       // System.out.println("controller - msgggs " + messageDto);
       // System.out.println(userPrincipal.getUsername());
        return messageDto;
    }

    @MessageMapping("/chat/addUser")
    @SendTo("/sub/public")
    public MessageDto addUser(@Payload MessageDto messageDto,
                           SimpMessageHeaderAccessor headerAccessor) {
        //headerAccessor.getSessionAttributes().put("userId", messageDto.getUserId());
        return messageDto;
    }

    @MessageMapping("/chat/self/message")
    public void sendSelfMessage(@Payload MessageDto messageDto, @AuthenticationPrincipal UserPrincipal userPrincipal,
                            SimpMessageHeaderAccessor headerAccessor) {
        chatService.sentPublicMessage(messageDto);
    }

    @MessageMapping("/chat/private/message")
    public void sendPrivateMessage(@Payload MessageDto messageDto, @AuthenticationPrincipal UserPrincipal userPrincipal,
                                   SimpMessageHeaderAccessor headerAccessor) {
        //chatService.sentPrivateMessage(messageDto);
    }

    @MessageMapping("/chat/group/message")
    public void sendGroupMessage(@Payload MessageDto messageDto, @AuthenticationPrincipal UserPrincipal userPrincipal,
                            SimpMessageHeaderAccessor headerAccessor) {

        chatService.sentPublicMessage(messageDto);
    }



}
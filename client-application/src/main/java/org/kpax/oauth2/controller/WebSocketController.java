package org.kpax.oauth2.controller;


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
    public Message sendMessage(@Payload Message chatMessage, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("controller - msgggs " + chatMessage);
        System.out.println(userPrincipal.getUsername());
        return chatMessage;
    }

    @MessageMapping("/chat/addUser")
    @SendTo("/sub/public")
    public Message addUser(@Payload Message chatMessage,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("userId", chatMessage.getUserId());
        return chatMessage;
    }

    @MessageMapping("/chat/self/message")
    public void sendSelfMessage(@Payload Message message, @AuthenticationPrincipal UserPrincipal userPrincipal,
                            SimpMessageHeaderAccessor headerAccessor) {
        chatService.sentPrivateMessage(message);
    }

    @MessageMapping("/chat/private/message")
    public void sendPrivateMessage(@Payload Message message, @AuthenticationPrincipal UserPrincipal userPrincipal,
                            SimpMessageHeaderAccessor headerAccessor) {
        chatService.sentPrivateMessage(message);
    }

    @MessageMapping("/chat/group/message")
    public void sendGroupMessage(@Payload Message message, @AuthenticationPrincipal UserPrincipal userPrincipal,
                            SimpMessageHeaderAccessor headerAccessor) {

        chatService.sentPublicMessage(message);
    }



}
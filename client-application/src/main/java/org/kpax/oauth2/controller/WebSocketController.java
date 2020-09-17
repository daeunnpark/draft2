package org.kpax.oauth2.controller;


import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.UserPrincipal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat/message")
    @SendTo("/sub/public")
    public Message sendMessage(@Payload Message chatMessage, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("controller - msgggs" + chatMessage);
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


}
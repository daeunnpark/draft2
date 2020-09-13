package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.service.chat.ChatService;
import org.kpax.oauth2.service.message.MessageService;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getChats(@AuthenticationPrincipal UserPrincipal principal) {
        Set<Chat> chats = userService.findById(principal.getId()).get().getChats();
        Object data = Collections.singletonMap("chats", chats);
        return new ApiResponse(true, data);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createChat(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Chat chat) {
        //chatService.create(principal.getId(),chat);
        Object data = Collections.singletonMap("chat", chatService.create(principal.getId(),chat));
        return new ApiResponse(true, data);
    }

    @DeleteMapping(value = "/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deleteChat(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
        chatService.exit(principal.getId(), chatId);
        return new ApiResponse(true, null);
    }

    @GetMapping(value = "/{chatId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getMessages(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
        Object data = Collections.singletonMap("messages", messageService.findRecentMsg(chatId));
        return new ApiResponse(true, data);
    }

    @GetMapping(value = "/{chatId}/members", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getChatMembers(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
        Object data = Collections.singletonMap("members",chatService.findById(chatId).getMembers());
        return new ApiResponse(true, data);
    }

}



package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.repository.UserRepository;
import org.kpax.oauth2.service.chat.ChatService;
import org.kpax.oauth2.service.chat.IChatService;
import org.kpax.oauth2.service.message.MessageService;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getChats(@AuthenticationPrincipal UserPrincipal principal) {
        Set<Chat> chats = userService.findById(principal.getId()).get().getChats();
        return new ApiResponse(true, chats);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createChat(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Chat chat) {
        chatService.save(chat);
        return new ApiResponse(true, userService.findById(principal.getId()).get());
    }

    @DeleteMapping(value = "/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deleteChat(@AuthenticationPrincipal UserPrincipal principal,@PathVariable Long chatId) {
        chatService.deleteById(chatId);
        return new ApiResponse(true, null);
    }

    @GetMapping(value = "/{chatId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getMessages(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
        return new ApiResponse(true, messageService.findRecentMsg(chatId));
    }

    @GetMapping(value = "/{chatId}/members", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getChatMembers(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
       chatService.findById(chatId).getMembers();
        return new ApiResponse(true, chatService.findById(chatId).getMembers());
    }

    @GetMapping(value = "/{chatId}/members", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getUsers(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
        chatService.findById(chatId).getMembers();
        return new ApiResponse(true, chatService.findById(chatId).getMembers());
    }








}



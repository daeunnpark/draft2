package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.User;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.service.chat.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @GetMapping( produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse getChats(@AuthenticationPrincipal UserPrincipal principal) {
        ApiResponse res = new ApiResponse(true, chatService.findByUserId(principal.getId()));
        System.out.println(res);
        return res;
    }

    /*
    @PostMapping(value="/", consumes = "application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ApiResponse createChat(@AuthenticationPrincipal User user, @RequestBody Chat chat) {
        ApiResponse res = new ApiResponse(true, chatService.create(user.getId()));
        // image, name, freindIds
        return res;
    }
*/

}



package org.kpax.oauth2.controller;

import net.minidev.json.JSONObject;
import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.service.chat.ChatService;
import org.kpax.oauth2.service.message.MessageService;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

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
    @GetMapping(value = "/{chatId}/translate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse translate(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String clientId = "RbKDSUzyil08gSg4uTQY";
        String clientSecret = "ci7o5IPttV";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.add("X-Naver-Client-Id", clientId);
        requestHeaders.add("X-Naver-Client-Secret", clientSecret);

        String text = "";
        List<Message> messages = messageService.findRecentMsg(chatId);
        for(Message m : messages){
            text+="//" + m.getContent();
        }

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("source", "ko");
        map.add("target", "en");
        map.add("text", text);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, requestHeaders);

        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(
                apiURL,
                HttpMethod.POST,
                request,
                JSONObject.class
        );


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("response received");
            System.out.println(responseEntity.getBody());
            //String [] translated = responseEntity.getBody().get("message");.//split("//");
            int nth=0;
            for(Message m : messages){
               //s m.setContent(translated[nth]);
            }
        } else {
            System.out.println("error occurred");
            System.out.println(responseEntity.getStatusCode());
        }


        Object data = Collections.singletonMap("translated_messages",messages);
        return new ApiResponse(true, data);
    }

}



package org.kpax.oauth2.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.kpax.oauth2.dto.model.ChatDto;
import org.kpax.oauth2.dto.model.MessageDto;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getChats(@AuthenticationPrincipal UserPrincipal principal) {
        List<ChatDto> chats = userService.findById(principal.getId()).getChats();
        return new ApiResponse(true, "chats", chats);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse createChat(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ChatDto chatDto) {
        ChatDto newChat = chatService.create(principal.getId(), chatDto);
        return new ApiResponse(true, "chat", newChat);
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deleteChat(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String json) throws IOException {
        HashMap myMap = new HashMap<String, List<Long>>();
        ObjectMapper objectMapper = new ObjectMapper();
        myMap = objectMapper.readValue(json, HashMap.class);
        List<Long> exitChatIds = chatService.exit(principal.getId(), (List<Integer>) myMap.get("chatIds"));
        return new ApiResponse(true, "chatIds", exitChatIds);
    }

    @GetMapping(value = "/{chatId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getMessages(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) {
        List<MessageDto> messages = messageService.findRecentMsg(chatId);
        return new ApiResponse(true, "messages", messages);
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
        List<MessageDto> messages = messageService.findRecentMsg(chatId);
        for (MessageDto m : messages) {
            text += "//" + m.getContent();
        }

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
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
            int nth = 0;
            for (MessageDto m : messages) {
                //s m.setContent(translated[nth]);
            }
        } else {
            System.out.println("error occurred");
            System.out.println(responseEntity.getStatusCode());
        }

        return new ApiResponse(true, "translated_messages", messages);
    }

}



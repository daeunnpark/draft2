package org.kpax.oauth2.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
    public ApiResponse translate(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long chatId) throws IOException {

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String clientId = "RbKDSUzyil08gSg4uTQY";
        String clientSecret = "ci7o5IPttV";

        String delimiter = "%";
        String text = "안녕하세요, 반가워요!";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.add("X-Naver-Client-Id", clientId);
        requestHeaders.add("X-Naver-Client-Secret", clientSecret);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("source", "ko");
        map.add("target", "en");
        map.add("text", text);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, requestHeaders);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                apiURL,
                HttpMethod.POST,
                request,
                String.class
        );

        MessageDto messageDto = null;

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(responseEntity.getBody(), JsonNode.class);

            String translatedText = jsonNode.get("message").get("result").get("translatedText").asText();

            messageDto = new MessageDto().setContent(translatedText);
        } else {
            System.out.println("error occurred");
            System.out.println(responseEntity.getStatusCode());
        }

        return new ApiResponse(true, "translated_message", messageDto);
    }

}



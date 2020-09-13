package org.kpax.oauth2.payload;

import lombok.Getter;
import lombok.Setter;
import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;
import org.kpax.oauth2.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
    /*
    private User user;
    private Set<User> users;
    private Set<Chat> chats;
    private Chat chat;
    private Set<Message> messages;
    private Set<User> members;
    s
     */

    public ApiResponse(boolean success, Object data) {
        this.code = success? 200 : 500;
        this.message = success? "OK" : "ERROR";
        this.data = data;
    }

}

package org.kpax.oauth2.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

@Getter
@Setter
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public ApiResponse(boolean success, String key, Object value) {
        this.code = success ? 200 : 500;
        this.message = success ? "OK" : "ERROR";
        this.data = Collections.singletonMap(key, value);
    }
}

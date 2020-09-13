package org.kpax.oauth2.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    public ApiResponse(boolean success, Object data) {
        this.code = success? 200 : 500;
        this.message = success? "OK" : "ERROR";
        this.data = data;
    }

}

package org.kpax.oauth2.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.kpax.oauth2.model.User;

import java.util.Date;


@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {
    private Long id;
    private String type;
    private ChatDto chat;
    private Long userId;
    private String content;
    private Date sentAt;
    private Integer unreadCnt;
}

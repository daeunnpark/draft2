package org.kpax.oauth2.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kpax.oauth2.model.Chat;
import org.kpax.oauth2.model.Message;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Accessors(chain = true)
public class MessageDto {
    private Long id;
    private String type;
    private Long chatId;
    private Long userId;
    private String content;
    private Date sentAt;
    private Integer unreadCnt;
}

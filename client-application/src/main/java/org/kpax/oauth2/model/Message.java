package org.kpax.oauth2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class Message {
    public enum MessageType {
        EMOTICON,
        TEXT,
        NOTI
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Chat chat;
    private Long userId;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
    private Integer unreadCnt;
}

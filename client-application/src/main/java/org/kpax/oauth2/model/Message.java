package org.kpax.oauth2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @OneToOne
    private Chat chat;
    @OneToOne
    private User user;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
    private Integer unreadCnt;
    public enum MessageType {
        EMOTICON,
        TEXT,
        NOTI
    }

}

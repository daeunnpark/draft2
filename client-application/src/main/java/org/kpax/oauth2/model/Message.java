package org.kpax.oauth2.model;

import javax.persistence.*;


@Entity
public class Message {
    public enum MessageType{
        TEXT,
        PHOTO
    }
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
    private String sentAt;
    private Integer unreadCnt;
}

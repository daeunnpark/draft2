package org.kpax.oauth2.model;

import javax.persistence.*;

enum MessageType{
    TEXT,
    PHOTO
}
@Entity
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
    private String sentAt;
    private Integer unreadCnt;
}

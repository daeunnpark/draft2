package org.kpax.oauth2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

enum ChatType{
    PRIVATE,
    GROUP
}

@Entity
public class Chat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ChatType type;
    private String name;


    @ManyToMany
    @JoinTable(name= "chat_user",
            joinColumns = @JoinColumn(name= "chat_id"),
            inverseJoinColumns = @JoinColumn(name= "user_id"))
    private Set<User> members = new HashSet<>();
    @OneToOne
    private Message lastMessage;
    @OneToOne
    private Message lastAt;
    @OneToOne
    private Media media;
    private Integer unreadCnt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getLast_message() {
        return lastMessage;
    }

    public void setLast_message(Message last_message) {
        this.lastMessage = last_message;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Integer getUnreadCnt() {
        return unreadCnt;
    }

    public void setUnreadCnt(Integer unreadCnt) {
        this.unreadCnt = unreadCnt;
    }


    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

}

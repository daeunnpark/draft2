package org.kpax.oauth2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class Chat {

    public enum ChatType{
        PRIVATE,
        GROUP
    }

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
    @ElementCollection(targetClass=Long.class)
    private Set<Long> friendIds = new HashSet<>();
    @OneToOne
    private Message lastMessage;
    @OneToOne
    private Message lastAt;
    private String image;
    private Integer unreadCnt;



}

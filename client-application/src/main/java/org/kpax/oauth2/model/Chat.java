package org.kpax.oauth2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


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
    private ChatType type=ChatType.PRIVATE;
    private String name;

    @ManyToMany
    @JoinTable(name= "chat_user",
            joinColumns = @JoinColumn(name= "chat_id"),
            inverseJoinColumns = @JoinColumn(name= "user_id"))
    private Set<User> members = new HashSet<>();

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Long> friendIds = new HashSet<>();
    @OneToOne
    private Message lastMessage;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAt;
    private String image;
    private Integer unreadCnt=0;



}

package org.kpax.oauth2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatType type = ChatType.PRIVATE;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members = new ArrayList<>();

    @Transient
    private Set<Long> friendIds = new HashSet<>();

    @OneToOne
    private Message lastMessage;

    @Temporal(TemporalType.TIMESTAMP)

    private Date lastAt;

    private String image;

    private Integer unreadCnt = 0;

    public enum ChatType {
        SELF,
        PRIVATE,
        GROUP
    }


}

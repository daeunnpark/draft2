package org.kpax.oauth2.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String password;
    private String username;
    private String email;
    private String phone;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();
}
package org.kpax.oauth2.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String image;

	@JsonIgnore
	private String password;
	@Getter(value = AccessLevel.NONE)
	private String username;
	@Getter(value = AccessLevel.NONE)
	private String email;
	@Getter(value = AccessLevel.NONE)
	private String phone;

	@JsonIgnore
	@ManyToMany
	private Set<Chat> chats = new HashSet<>();

}
package org.kpax.oauth2.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String image = "https://www.azocleantech.com/images/Article_Images/ImageForArticle_1061_15837536188863190.png";

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@Getter(value = AccessLevel.NONE)
	private String username;
	@Getter(value = AccessLevel.NONE)
	private String email;
	@Getter(value = AccessLevel.NONE)
	private String phone;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Getter(AccessLevel.NONE)
	@ManyToMany(mappedBy = "members")
	private List<Chat> chats = new ArrayList<>();

	public List<Chat> getChats() {
		System.out.println("**** custom getchat()");
		this.chats.sort(Comparator.comparing(Chat::getLastAt).reversed());
		return this.chats;
	}


}
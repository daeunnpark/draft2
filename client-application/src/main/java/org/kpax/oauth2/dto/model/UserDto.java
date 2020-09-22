package org.kpax.oauth2.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Comparator;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String name;
    private String image;
    @JsonIgnore
    @Getter(value = AccessLevel.NONE)
    private List<ChatDto> chats;
    private boolean active;

    public List<ChatDto> getChats() {
        this.chats.sort(Comparator.comparing(ChatDto::getLastAt).reversed());
        return this.chats;
    }

}

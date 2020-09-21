package org.kpax.oauth2.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatDto {
    private Long id;
    private String type;
    private String name;
    //@Getter(AccessLevel.NONE)
    private List<UserDto> members = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Long> friendIds;
    private MessageDto lastMessage;
    private Date lastAt;
    private String image;
    private Integer unreadCnt;

/*
    public List<UserDto> getMembers() {
        System.out.println(this.members);
        this.members.sort(Comparator.comparing(UserDto::getName));
        return this.members;
    }
*/


}

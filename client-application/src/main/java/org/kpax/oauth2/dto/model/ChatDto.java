package org.kpax.oauth2.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ChatDto {
    private Long id;
    private String type;
    private String name;
    private List<UserDto> members;
    private Set<Long> friendIds;
    private Long lastMessageId;
    private Date lastAt;
    private String image;
    private Integer unreadCnt;
}

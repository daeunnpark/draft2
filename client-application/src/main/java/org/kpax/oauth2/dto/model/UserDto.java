package org.kpax.oauth2.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String image;
    private String password;
    private String username;
    private String email;
    private String phone;
    private List<Long> chatIds;
}

package org.kpax.oauth2.dto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kpax.oauth2.model.Media;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Accessors(chain = true)
public class MediaDto {
    private Long id;
    private String type;
    private String name;
    private Long size;
    private String url;
}

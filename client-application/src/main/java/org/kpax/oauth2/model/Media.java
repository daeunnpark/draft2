package org.kpax.oauth2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    private String name;

    private Long size;

    private String url;

    public enum MediaType {
        PHOTO,
        VIDEO
    }
}

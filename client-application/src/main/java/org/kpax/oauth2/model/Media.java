package org.kpax.oauth2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class Media {
    public enum MediaType{
        PHOTO,
        VIDEO
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    private String name;
    private Long size;
    private String url;
}

package org.kpax.oauth2.model;

import javax.persistence.*;


@Entity
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

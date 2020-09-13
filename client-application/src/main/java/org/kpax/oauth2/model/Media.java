package org.kpax.oauth2.model;

import javax.persistence.*;

enum MediaType{
    PHOTO,
    VIDEO
}

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    private String name;
    private Long size;
    private String url;
}

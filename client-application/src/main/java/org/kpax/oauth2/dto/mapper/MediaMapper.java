package org.kpax.oauth2.dto.mapper;

import org.kpax.oauth2.dto.model.MediaDto;
import org.kpax.oauth2.model.Media;

public class MediaMapper {
    public static MediaDto toMediaDto(Media media) {
        return new MediaDto()
                .setId(media.getId())
                .setType(media.getType().toString())
                .setName(media.getName())
                .setSize(media.getSize())
                .setUrl(media.getUrl());
    }
}

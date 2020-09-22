package org.kpax.oauth2.dto.mapper;

import javax.annotation.Generated;
import org.kpax.oauth2.dto.mapper.config.CycleAvoidingMappingContext;
import org.kpax.oauth2.dto.model.MediaDto;
import org.kpax.oauth2.model.Media;
import org.kpax.oauth2.model.Media.MediaType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-22T16:56:33+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class MediaMapperImpl implements MediaMapper {

    @Override
    public Media toMedia(MediaDto mediaDto, CycleAvoidingMappingContext context) {
        Media target = context.getMappedInstance( mediaDto, Media.class );
        if ( target != null ) {
            return target;
        }

        if ( mediaDto == null ) {
            return null;
        }

        Media media = new Media();

        context.storeMappedInstance( mediaDto, media );

        media.setId( mediaDto.getId() );
        if ( mediaDto.getType() != null ) {
            media.setType( Enum.valueOf( MediaType.class, mediaDto.getType() ) );
        }
        media.setName( mediaDto.getName() );
        media.setSize( mediaDto.getSize() );
        media.setUrl( mediaDto.getUrl() );

        return media;
    }

    @Override
    public MediaDto fromMedia(Media media, CycleAvoidingMappingContext context) {
        MediaDto target = context.getMappedInstance( media, MediaDto.class );
        if ( target != null ) {
            return target;
        }

        if ( media == null ) {
            return null;
        }

        MediaDto mediaDto = new MediaDto();

        context.storeMappedInstance( media, mediaDto );

        mediaDto.setId( media.getId() );
        if ( media.getType() != null ) {
            mediaDto.setType( media.getType().name() );
        }
        mediaDto.setName( media.getName() );
        mediaDto.setSize( media.getSize() );
        mediaDto.setUrl( media.getUrl() );

        return mediaDto;
    }
}

package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.GenreDTO;
import com.bookstats.BookStats.entity.Genre;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T12:20:35+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Arch Linux)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDTO toDTO(Genre entity) {
        if ( entity == null ) {
            return null;
        }

        GenreDTO genreDTO = new GenreDTO();

        genreDTO.setId( entity.getId() );
        genreDTO.setName( entity.getName() );

        return genreDTO;
    }

    @Override
    public Genre toEntity(GenreDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setId( dto.getId() );
        genre.setName( dto.getName() );

        return genre;
    }

    @Override
    public List<GenreDTO> toDTOList(List<Genre> entities) {
        if ( entities == null ) {
            return null;
        }

        List<GenreDTO> list = new ArrayList<GenreDTO>( entities.size() );
        for ( Genre genre : entities ) {
            list.add( toDTO( genre ) );
        }

        return list;
    }

    @Override
    public Set<GenreDTO> toDTOSet(Set<Genre> entities) {
        if ( entities == null ) {
            return null;
        }

        Set<GenreDTO> set = new LinkedHashSet<GenreDTO>( Math.max( (int) ( entities.size() / .75f ) + 1, 16 ) );
        for ( Genre genre : entities ) {
            set.add( toDTO( genre ) );
        }

        return set;
    }
}

package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.GenreDTO;
import com.bookstats.BookStats.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDTO toDTO(Genre entity);

    @Mapping(target = "books", ignore = true)
    Genre toEntity(GenreDTO dto);

    List<GenreDTO> toDTOList(List<Genre> entities);

    Set<GenreDTO> toDTOSet(Set<Genre> entities);
}
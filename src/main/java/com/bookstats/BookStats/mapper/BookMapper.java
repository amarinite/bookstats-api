package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.request.BookRequestDTO;
import com.bookstats.BookStats.dto.response.BookResponseDTO;
import com.bookstats.BookStats.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "userBooks", ignore = true)
    Book toEntity(BookRequestDTO dto);

    @Mapping(target = "genres", source = "genres")
    BookResponseDTO toResponseDTO(Book entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "userBooks", ignore = true)
    void updateEntityFromDTO(BookRequestDTO dto, @MappingTarget Book entity);

    List<BookResponseDTO> toResponseDTOList(List<Book> entities);
}
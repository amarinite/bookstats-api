package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.request.UserBookRequestDTO;
import com.bookstats.BookStats.dto.response.UserBookResponseDTO;
import com.bookstats.BookStats.entity.UserBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapper.class, UserMapper.class})
public interface UserBookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserBook toEntity(UserBookRequestDTO dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "book", source = "book")
    UserBookResponseDTO toResponseDTO(UserBook entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(UserBookRequestDTO dto, @MappingTarget UserBook entity);

    List<UserBookResponseDTO> toResponseDTOList(List<UserBook> entities);
}

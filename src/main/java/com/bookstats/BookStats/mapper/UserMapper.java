package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.request.UserRequestDTO;
import com.bookstats.BookStats.dto.response.UserResponseDTO;
import com.bookstats.BookStats.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Convert UserRequestDTO to User entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userBooks", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // Handle separately in service
    User toEntity(UserRequestDTO dto);

    // Convert User entity to UserResponseDTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createdAt", source = "createdAt")
    UserResponseDTO toResponseDTO(User entity);

    // Update existing User entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userBooks", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // Handle separately in service
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User entity);

    // Convert list of entities to DTOs
    List<UserResponseDTO> toResponseDTOList(List<User> entities);
}


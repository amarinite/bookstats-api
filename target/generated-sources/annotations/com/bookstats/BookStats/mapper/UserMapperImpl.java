package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.request.UserRequestDTO;
import com.bookstats.BookStats.dto.response.UserResponseDTO;
import com.bookstats.BookStats.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T12:20:35+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Arch Linux)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );

        return user;
    }

    @Override
    public UserResponseDTO toResponseDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( entity.getId() );
        userResponseDTO.setUsername( entity.getUsername() );
        userResponseDTO.setEmail( entity.getEmail() );
        userResponseDTO.setCreatedAt( entity.getCreatedAt() );

        return userResponseDTO;
    }

    @Override
    public void updateEntityFromDTO(UserRequestDTO dto, User entity) {
        if ( dto == null ) {
            return;
        }

        entity.setUsername( dto.getUsername() );
        entity.setEmail( dto.getEmail() );
    }

    @Override
    public List<UserResponseDTO> toResponseDTOList(List<User> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserResponseDTO> list = new ArrayList<UserResponseDTO>( entities.size() );
        for ( User user : entities ) {
            list.add( toResponseDTO( user ) );
        }

        return list;
    }
}

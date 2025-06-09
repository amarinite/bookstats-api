package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.request.UserBookRequestDTO;
import com.bookstats.BookStats.dto.response.UserBookResponseDTO;
import com.bookstats.BookStats.entity.User;
import com.bookstats.BookStats.entity.UserBook;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T12:20:35+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Arch Linux)"
)
@Component
public class UserBookMapperImpl implements UserBookMapper {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public UserBook toEntity(UserBookRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserBook userBook = new UserBook();

        userBook.setStatus( dto.getStatus() );
        userBook.setRating( dto.getRating() );
        userBook.setStartDate( dto.getStartDate() );
        userBook.setFinishDate( dto.getFinishDate() );
        userBook.setNotes( dto.getNotes() );

        return userBook;
    }

    @Override
    public UserBookResponseDTO toResponseDTO(UserBook entity) {
        if ( entity == null ) {
            return null;
        }

        UserBookResponseDTO userBookResponseDTO = new UserBookResponseDTO();

        userBookResponseDTO.setUserId( entityUserId( entity ) );
        userBookResponseDTO.setBook( bookMapper.toResponseDTO( entity.getBook() ) );
        userBookResponseDTO.setId( entity.getId() );
        userBookResponseDTO.setStatus( entity.getStatus() );
        userBookResponseDTO.setRating( entity.getRating() );
        userBookResponseDTO.setStartDate( entity.getStartDate() );
        userBookResponseDTO.setFinishDate( entity.getFinishDate() );
        userBookResponseDTO.setNotes( entity.getNotes() );
        userBookResponseDTO.setCreatedAt( entity.getCreatedAt() );

        return userBookResponseDTO;
    }

    @Override
    public void updateEntityFromDTO(UserBookRequestDTO dto, UserBook entity) {
        if ( dto == null ) {
            return;
        }

        entity.setStatus( dto.getStatus() );
        entity.setRating( dto.getRating() );
        entity.setStartDate( dto.getStartDate() );
        entity.setFinishDate( dto.getFinishDate() );
        entity.setNotes( dto.getNotes() );
    }

    @Override
    public List<UserBookResponseDTO> toResponseDTOList(List<UserBook> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserBookResponseDTO> list = new ArrayList<UserBookResponseDTO>( entities.size() );
        for ( UserBook userBook : entities ) {
            list.add( toResponseDTO( userBook ) );
        }

        return list;
    }

    private Long entityUserId(UserBook userBook) {
        if ( userBook == null ) {
            return null;
        }
        User user = userBook.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

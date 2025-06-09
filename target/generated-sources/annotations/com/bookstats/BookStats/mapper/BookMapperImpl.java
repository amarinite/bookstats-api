package com.bookstats.BookStats.mapper;

import com.bookstats.BookStats.dto.request.BookRequestDTO;
import com.bookstats.BookStats.dto.response.BookResponseDTO;
import com.bookstats.BookStats.entity.Book;
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
public class BookMapperImpl implements BookMapper {

    @Autowired
    private GenreMapper genreMapper;

    @Override
    public Book toEntity(BookRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setTitle( dto.getTitle() );
        book.setAuthor( dto.getAuthor() );
        book.setIsbn( dto.getIsbn() );
        book.setPages( dto.getPages() );
        book.setLanguage( dto.getLanguage() );
        book.setCountry( dto.getCountry() );
        book.setAuthorGender( dto.getAuthorGender() );
        book.setPublishedYear( dto.getPublishedYear() );
        book.setCoverUrl( dto.getCoverUrl() );

        return book;
    }

    @Override
    public BookResponseDTO toResponseDTO(Book entity) {
        if ( entity == null ) {
            return null;
        }

        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        bookResponseDTO.setGenres( genreMapper.toDTOSet( entity.getGenres() ) );
        bookResponseDTO.setId( entity.getId() );
        bookResponseDTO.setTitle( entity.getTitle() );
        bookResponseDTO.setAuthor( entity.getAuthor() );
        bookResponseDTO.setIsbn( entity.getIsbn() );
        bookResponseDTO.setPages( entity.getPages() );
        bookResponseDTO.setLanguage( entity.getLanguage() );
        bookResponseDTO.setCountry( entity.getCountry() );
        bookResponseDTO.setAuthorGender( entity.getAuthorGender() );
        bookResponseDTO.setPublishedYear( entity.getPublishedYear() );
        bookResponseDTO.setCoverUrl( entity.getCoverUrl() );

        return bookResponseDTO;
    }

    @Override
    public void updateEntityFromDTO(BookRequestDTO dto, Book entity) {
        if ( dto == null ) {
            return;
        }

        entity.setTitle( dto.getTitle() );
        entity.setAuthor( dto.getAuthor() );
        entity.setIsbn( dto.getIsbn() );
        entity.setPages( dto.getPages() );
        entity.setLanguage( dto.getLanguage() );
        entity.setCountry( dto.getCountry() );
        entity.setAuthorGender( dto.getAuthorGender() );
        entity.setPublishedYear( dto.getPublishedYear() );
        entity.setCoverUrl( dto.getCoverUrl() );
    }

    @Override
    public List<BookResponseDTO> toResponseDTOList(List<Book> entities) {
        if ( entities == null ) {
            return null;
        }

        List<BookResponseDTO> list = new ArrayList<BookResponseDTO>( entities.size() );
        for ( Book book : entities ) {
            list.add( toResponseDTO( book ) );
        }

        return list;
    }
}

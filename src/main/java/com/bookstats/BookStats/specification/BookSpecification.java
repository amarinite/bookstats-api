package com.bookstats.BookStats.specification;

import com.bookstats.BookStats.dto.BookSearchDTO;
import com.bookstats.BookStats.entity.Book;
import com.bookstats.BookStats.entity.Genre;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class BookSpecification {

    public static Specification<Book> withCriteria(BookSearchDTO searchDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(searchDTO.getTitle())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + searchDTO.getTitle().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(searchDTO.getAuthor())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("author")),
                        "%" + searchDTO.getAuthor().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(searchDTO.getLanguage())) {
                predicates.add(criteriaBuilder.equal(root.get("language"), searchDTO.getLanguage()));
            }

            if (searchDTO.getAuthorGender() != null) {
                predicates.add(criteriaBuilder.equal(root.get("authorGender"), searchDTO.getAuthorGender()));
            }

            if (searchDTO.getMinYear() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishedYear"), searchDTO.getMinYear()));
            }

            if (searchDTO.getMaxYear() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publishedYear"), searchDTO.getMaxYear()));
            }

            if (searchDTO.getGenreIds() != null && !searchDTO.getGenreIds().isEmpty()) {
                Join<Book, Genre> genreJoin = root.join("genres");
                predicates.add(genreJoin.get("id").in(searchDTO.getGenreIds()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

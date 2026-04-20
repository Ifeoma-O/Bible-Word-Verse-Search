package com.bible.versefilter.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bible.versefilter.model.Verse;

public interface VerseRepository extends JpaRepository<Verse, Long>{
    Optional<Verse> findByBookAndChapterAndVerse(
        String book,
        int chapter,
        int verse
    );

    @Query(value = """
        SELECT * FROM verses
        WHERE search_vector @@ plainto_tsquery('english', :keyword)
        """,
        countQuery = """
        SELECT count(*) FROM verses
        WHERE search_vector @@ plainto_tsquery('english', :keyword)
        """,
        nativeQuery = true)
    Page<Verse> searchFullText(
            @Param("keyword") String keyword,
            Pageable pageable
    );


}

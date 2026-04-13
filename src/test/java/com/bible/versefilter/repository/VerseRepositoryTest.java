package com.bible.versefilter.repository;

import com.bible.versefilter.model.Verse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VerseRepositoryTest {

    @Autowired
    private VerseRepository verseRepository;

    @BeforeEach
    void setUp() {
        Verse verse = new Verse();
        verse.setBook("John");
        verse.setChapter(3);
        verse.setVerse(16);
        verse.setText("For God so loved the world");

        verseRepository.save(verse);
    }

    @Test
    void shouldFindVerseByBookChapterVerse() {

        Optional<Verse> verse =
                verseRepository.findByBookAndChapterAndVerse("John", 3, 16);

        assertThat(verse).isPresent();
        assertThat(verse.get().getText())
                .isEqualTo("For God so loved the world");
    }
}
package com.bible.versefilter.service;

import com.bible.versefilter.dto.VerseResponse;
import com.bible.versefilter.model.Verse;
import com.bible.versefilter.repository.VerseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerseServiceTest {

    private VerseRepository verseRepository;
    private VerseService verseService;

    @BeforeEach
    void setUp() {
        verseRepository = mock(VerseRepository.class);
        verseService = new VerseService(verseRepository);
    }

    @Test
    void search_ReturnsVerseResponses() {
        // Arrange: create a dummy verse
        Verse verse = new Verse();
        verse.setBook("Genesis");
        verse.setChapter(1);
        verse.setVerse(1);
        verse.setText("In the beginning God created the heavens and the earth.");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Verse> pageOfVerses = new PageImpl<>(List.of(verse), pageable, 1);

        when(verseRepository.searchFullText("beginning", pageable)).thenReturn(pageOfVerses);

        Page<VerseResponse> result = verseService.search("beginning", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        VerseResponse response = result.getContent().get(0);
        assertEquals("Genesis", response.book());
        assertEquals(1, response.chapter());
        assertEquals(1, response.verse());
        assertEquals("In the beginning God created the heavens and the earth.", response.text());

        verify(verseRepository, times(1)).searchFullText("beginning", pageable);
    }

    @Test
    void search_ReturnsEmptyPage_WhenNoMatch() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Verse> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(verseRepository.searchFullText("nonexistent", pageable)).thenReturn(emptyPage);

        Page<VerseResponse> result = verseService.search("nonexistent", 0, 10);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(verseRepository, times(1)).searchFullText("nonexistent", pageable);
    }
}
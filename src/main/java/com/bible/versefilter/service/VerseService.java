package com.bible.versefilter.service;

import com.bible.versefilter.dto.VerseResponse;
import com.bible.versefilter.model.Verse;
import com.bible.versefilter.repository.VerseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class VerseService {

    private final VerseRepository verseRepository;

    public VerseService(VerseRepository verseRepository) {
        this.verseRepository = verseRepository;
    }

    public Page<VerseResponse> search(String keyword, int page, int size) {
        return verseRepository
                .searchFullText(keyword, PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    private VerseResponse mapToResponse(Verse verse){
        return new VerseResponse(
            verse.getBook(),
            verse.getChapter(),
            verse.getVerse(),
            verse.getText()
        );
    }

}

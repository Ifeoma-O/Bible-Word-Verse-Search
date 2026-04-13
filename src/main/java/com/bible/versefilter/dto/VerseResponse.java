package com.bible.versefilter.dto;

public record VerseResponse(
    String book,
    int chapter,
    int verse,
    String text) {
    

}

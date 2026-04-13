package com.bible.versefilter.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "verses")
public class Verse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String book;
    @Column(nullable = false)
    private int chapter;
    @Column(nullable = false)
    private int verse;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @ElementCollection
    @CollectionTable(name = "verse_topics", joinColumns = @JoinColumn(name = "verse_id"))
    @Column(name = "topic")
    public List<String> topics;


    public Verse(){

    }

    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }
    public int getChapter() {
        return chapter;
    }
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
    public int getVerse() {
        return verse;
    }
    public void setVerse(int verse) {
        this.verse = verse;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public List<String> getTopics() {
        return topics;
    }
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    
}

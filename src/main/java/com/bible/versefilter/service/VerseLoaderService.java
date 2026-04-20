package com.bible.versefilter.service;

import org.springframework.context.annotation.Profile;
import com.bible.versefilter.model.Verse;
import com.bible.versefilter.repository.VerseRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



@Service
@Profile("!test")
public class VerseLoaderService {
    private static final Logger log = LoggerFactory.getLogger(VerseLoaderService.class);

    private final VerseRepository verseRepository;

    public VerseLoaderService(VerseRepository verseRepository) {
        this.verseRepository = verseRepository;
    }

    @PostConstruct
    @Transactional
    public void loadVerses() {
        // 1. CHECK FIRST: Only load if the table is empty
        if (verseRepository.count() > 0) {
            System.out.println("Verses already loaded. Skipping CSV import.");
            return; 
        }

        System.out.println("Database is empty. Starting CSV load...");

        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("KJV.csv")))
                .withSkipLines(1) 
                .build()) {

            String[] parts;
            List<Verse> versesToSave = new ArrayList<>(); 

            while ((parts = reader.readNext()) != null) {
                if (parts.length < 4) {
                    log.warn("Skipping malformed line: {}", (Object) parts);
                    continue;
                }

                try {
                    Verse v = new Verse();
                    v.setBook(parts[0].trim());
                    v.setChapter(Integer.parseInt(parts[1].trim()));
                    v.setVerse(Integer.parseInt(parts[2].trim()));
                    v.setText(parts[3].trim());
                    
                    versesToSave.add(v);
                } catch (NumberFormatException nfe) {
                    log.error("Data type mismatch for {}: Expected numbers but got Chapter='{}' and Verse='{}'", 
                            parts[0], parts[1], parts[2]);
                }
            }
            verseRepository.saveAll(versesToSave);
            System.out.println("CSV loaded successfully! Total verses: " + versesToSave.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

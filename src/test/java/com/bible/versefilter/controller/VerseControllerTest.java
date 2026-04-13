package com.bible.versefilter.controller;

import com.bible.versefilter.dto.VerseResponse;
import com.bible.versefilter.service.VerseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VerseController.class)
public class VerseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerseService verseService;

    @Test
    void shouldSearchVerses() throws Exception {
        // Prepare mock data
        VerseResponse verse = new VerseResponse("Genesis", 1, 1, "In the beginning...");
        Page<VerseResponse> page = new PageImpl<>(List.of(verse), PageRequest.of(0, 20), 1);

        // Mock service
        when(verseService.search("beginning", 0, 20)).thenReturn(page);

        // Perform request and assert
        mockMvc.perform(get("/api/v1/verses/search")
                        .param("keyword", "beginning")
                        .param("page", "0")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].book").value("Genesis"))
                .andExpect(jsonPath("$.content[0].chapter").value(1))
                .andExpect(jsonPath("$.content[0].verse").value(1))
                .andExpect(jsonPath("$.content[0].text").value("In the beginning..."));
    }
}
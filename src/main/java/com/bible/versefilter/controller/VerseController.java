package com.bible.versefilter.controller;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.bible.versefilter.dto.VerseResponse;
import com.bible.versefilter.service.VerseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/v1/verses")
public class VerseController {
    
    private final VerseService verseService;

    public VerseController(VerseService verseService){
        this.verseService = verseService;
    }

    @GetMapping("/search")
    @Operation(summary = "Search verses by keyword")
    public Page<VerseResponse> search(
            @Parameter(description = "Search keyword")
            @RequestParam String keyword,

            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of results per page")
            @RequestParam(defaultValue = "20") int size
        ) {
        return verseService.search(keyword, page, size);
    }
}

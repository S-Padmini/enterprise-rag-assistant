package com.rag.enterpriseragassistant.controller;

import com.rag.enterpriseragassistant.service.VectorStoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {

    private final VectorStoreService vectorStoreService;

    public SearchController(VectorStoreService vectorStoreService) {
        this.vectorStoreService = vectorStoreService;
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam String query) {

        return vectorStoreService.search(query);

    }
}
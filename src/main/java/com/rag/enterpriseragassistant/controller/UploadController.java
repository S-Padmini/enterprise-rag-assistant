package com.rag.enterpriseragassistant.controller;

import com.rag.enterpriseragassistant.service.DocumentExtractionService;
import com.rag.enterpriseragassistant.service.SemanticChunkingService;
import com.rag.enterpriseragassistant.service.VectorStoreService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UploadController {

    private final DocumentExtractionService extractionService;
    private final SemanticChunkingService chunkingService;
    private final VectorStoreService vectorStoreService;


    public UploadController(
            DocumentExtractionService extractionService,
            SemanticChunkingService chunkingService,
            VectorStoreService vectorStoreService) {

        this.extractionService = extractionService;
        this.chunkingService = chunkingService;
        this.vectorStoreService = vectorStoreService;
    }


    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("file") MultipartFile file) {

        try {

            String extractedText =
                    extractionService.extractText(file);


            List<String> chunks =
                    chunkingService.createChunks(extractedText);


            vectorStoreService.storeChunks(chunks);


            return chunks;


        } catch (Exception e) {

            return List.of("Error: " + e.getMessage());
        }
    }
}
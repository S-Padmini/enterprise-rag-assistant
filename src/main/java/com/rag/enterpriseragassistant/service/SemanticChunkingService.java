package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemanticChunkingService {

    public List<String> createChunks(String text) {

        List<String> chunks = new ArrayList<>();

        String[] sentences = text.split("\\.");

        for (String sentence : sentences) {

            if (!sentence.trim().isEmpty()) {
                chunks.add(sentence.trim() + ".");
            }
        }

        return chunks;
    }
}
package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmbeddingService {

    public List<Double> generateEmbedding(String text) {

        List<Double> embedding = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return embedding;
        }

        // Placeholder embedding
        embedding.add((double) text.length());
        embedding.add((double) text.split("\\s+").length);
        embedding.add((double) text.hashCode());

        return embedding;
    }
}
package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VectorStoreService {

    private final EmbeddingService embeddingService;

    private final List<String> storedChunks = new ArrayList<>();

    private final List<List<Double>> storedEmbeddings = new ArrayList<>();


    public VectorStoreService(EmbeddingService embeddingService) {

        this.embeddingService = embeddingService;
    }


    public void storeChunks(List<String> chunks) {

        for (String chunk : chunks) {

            storedChunks.add(chunk);

            List<Double> embedding =
                    embeddingService.generateEmbedding(chunk);

            storedEmbeddings.add(embedding);
        }
    }


    public List<String> search(String query) {

        List<SearchResult> matchedResults = new ArrayList<>();

        String[] keywords = query
                .toLowerCase()
                .replace("?", "")
                .split(" ");


        for (String chunk : storedChunks) {

            String lowerChunk = chunk.toLowerCase();

            int matchCount = 0;


            for (String keyword : keywords) {

                if (keyword.length() > 3 &&
                        lowerChunk.contains(keyword)) {

                    matchCount++;
                }
            }


            // Store chunk with its relevance score
            if (matchCount > 0) {

                matchedResults.add(
                        new SearchResult(chunk, matchCount)
                );
            }
        }


        // Sort by highest relevance score
        matchedResults.sort(
                Comparator.comparingInt(SearchResult::getScore)
                        .reversed()
        );


        List<String> results = new ArrayList<>();

        for (SearchResult result : matchedResults) {

            results.add(result.getChunk());
        }


        return results;
    }


    public String getContext(String query) {

        List<String> results = search(query);

        if (results.isEmpty()) {

            return "";
        }

        return String.join("\n", results);
    }


    public List<List<Double>> getStoredEmbeddings() {

        return storedEmbeddings;
    }


    // Helper class for ranking search results
    private static class SearchResult {

        private final String chunk;
        private final int score;


        public SearchResult(String chunk, int score) {

            this.chunk = chunk;
            this.score = score;
        }


        public String getChunk() {

            return chunk;
        }


        public int getScore() {

            return score;
        }
    }
}

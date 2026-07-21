package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VectorStoreService {

    private final List<String> storedChunks = new ArrayList<>();


    public void storeChunks(List<String> chunks) {

        storedChunks.addAll(chunks);
    }


    public List<String> search(String query) {

        List<String> results = new ArrayList<>();

        String[] keywords = query
                .toLowerCase()
                .replace("?", "")
                .split(" ");


        for (String chunk : storedChunks) {

            String lowerChunk = chunk.toLowerCase();

            int matchCount = 0;


            for (String keyword : keywords) {

                if (keyword.length() > 3 && lowerChunk.contains(keyword)) {
                    matchCount++;
                }
            }


            // If important words match, return chunk
            if (matchCount > 0) {
                results.add(chunk);
            }
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
}
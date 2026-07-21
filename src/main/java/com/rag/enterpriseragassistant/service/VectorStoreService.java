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
    public List<String> search(String keyword) {

        List<String> results = new ArrayList<>();

        for (String chunk : storedChunks) {

            if (chunk.toLowerCase().contains(keyword.toLowerCase())) {
                results.add(chunk);
            }
        }

        return results;
    }
}
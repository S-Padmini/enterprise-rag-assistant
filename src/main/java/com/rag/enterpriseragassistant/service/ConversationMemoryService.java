package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationMemoryService {

    private final List<String> conversationHistory = new ArrayList<>();

    public void addConversation(String userQuestion, String aiResponse) {

        conversationHistory.add(
                "User: " + userQuestion +
                "\nAI: " + aiResponse
        );
    }

    public List<String> getConversationHistory() {

        return conversationHistory;
    }

    public String getFormattedHistory() {

        StringBuilder history = new StringBuilder();

        for (String conversation : conversationHistory) {
            history.append(conversation)
                   .append("\n\n");
        }

        return history.toString();
    }
}
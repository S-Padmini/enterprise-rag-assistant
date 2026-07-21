package com.rag.enterpriseragassistant.controller;

import com.rag.enterpriseragassistant.service.GitHubAIService;
import com.rag.enterpriseragassistant.service.VectorStoreService;
import com.rag.enterpriseragassistant.service.ConversationMemoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIChatController {

    private final GitHubAIService githubAIService;
    private final VectorStoreService vectorStoreService;
    private final ConversationMemoryService conversationMemoryService;


    public AIChatController(
            GitHubAIService githubAIService,
            VectorStoreService vectorStoreService,
            ConversationMemoryService conversationMemoryService) {

        this.githubAIService = githubAIService;
        this.vectorStoreService = vectorStoreService;
        this.conversationMemoryService = conversationMemoryService;
    }


    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {


        // Retrieve document context
        String context = vectorStoreService.getContext(question);


        // If no document context found, continue using conversation memory
        if (context.isBlank()) {

            context = "No new document context available. Use previous conversation history.";
        }


        // Retrieve previous conversation history
        String history = conversationMemoryService.getFormattedHistory();


        // Send context + question + history to AI
        String response = githubAIService.generateResponse(
                context,
                question,
                history
        );


        // Save current conversation
        conversationMemoryService.addConversation(
                question,
                response
        );


        return response;
    }
}
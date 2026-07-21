package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

@Service
public class RAGAgentService {

    private final VectorStoreService vectorStoreService;
    private final ConversationMemoryService conversationMemoryService;
    private final GitHubAIService githubAIService;


    public RAGAgentService(
            VectorStoreService vectorStoreService,
            ConversationMemoryService conversationMemoryService,
            GitHubAIService githubAIService) {

        this.vectorStoreService = vectorStoreService;
        this.conversationMemoryService = conversationMemoryService;
        this.githubAIService = githubAIService;
    }


    public String processQuestion(String question) {


        // Step 1: Retrieve relevant document context
        String documentContext =
                vectorStoreService.getContext(question);


        // Step 2: Retrieve previous conversation history
        String history =
                conversationMemoryService.getFormattedHistory();


        // Step 3: Combine document context + memory
        String finalContext;


        if (documentContext.isBlank()) {

            finalContext =
                    "Previous Conversation History:\n"
                    + history;

        } else {

            finalContext =
                    "Document Context:\n"
                    + documentContext
                    + "\n\nPrevious Conversation History:\n"
                    + history;
        }


        // Step 4: Send context + question + history to AI
        String response =
                githubAIService.generateResponse(
                        finalContext,
                        question,
                        history
                );


        // Step 5: Store conversation memory
        conversationMemoryService.addConversation(
                question,
                response
        );


        return response;
    }
}
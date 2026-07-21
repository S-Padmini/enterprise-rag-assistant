package com.rag.enterpriseragassistant.controller;

import com.rag.enterpriseragassistant.service.GitHubAIService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AIChatController {


    private final GitHubAIService githubAIService;


    public AIChatController(GitHubAIService githubAIService) {
        this.githubAIService = githubAIService;
    }


    @GetMapping("/ai-test")
    public String testAI() {

        return githubAIService.generateResponse(
                "Explain what is Enterprise RAG Assistant in simple words"
        );

    }

}
package com.rag.enterpriseragassistant.controller;

import com.rag.enterpriseragassistant.service.RAGAgentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AIChatController {


    private final RAGAgentService ragAgentService;


    public AIChatController(
            RAGAgentService ragAgentService) {

        this.ragAgentService = ragAgentService;
    }


    @GetMapping("/ask")
    public String askQuestion(
            @RequestParam String question) {


        return ragAgentService.processQuestion(question);
    }
}
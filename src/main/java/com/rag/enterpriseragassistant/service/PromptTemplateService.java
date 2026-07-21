package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {

    public String buildPrompt(String context, String question) {

        return """
                You are an Enterprise RAG Assistant.

                Answer ONLY using the document context below.

                If the answer is not found, clearly say:
                "I couldn't find that information in the uploaded document."

                -----------------------------
                DOCUMENT CONTEXT
                -----------------------------

                %s

                -----------------------------
                USER QUESTION
                -----------------------------

                %s

                -----------------------------
                ANSWER
                -----------------------------
                """.formatted(context, question);
    }
}
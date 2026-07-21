package com.rag.enterpriseragassistant.service;

import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {


    public String buildPrompt(
            String context,
            String question,
            String history) {


        return """
                You are an Enterprise RAG Assistant.

                Your task is to answer user questions using the provided document context.

                Follow these rules strictly:

                1. Use only the information available in the DOCUMENT CONTEXT.
                2. Use previous conversation history only when it helps understand the question.
                3. Do not create or assume information that is not present.
                4. If the answer is not available in the document context, respond:
                   "I couldn't find that information in the uploaded document."

                -----------------------------
                CONVERSATION HISTORY
                -----------------------------

                %s


                -----------------------------
                DOCUMENT CONTEXT
                -----------------------------

                %s


                -----------------------------
                USER QUESTION
                -----------------------------

                %s


                -----------------------------
                FINAL ANSWER
                -----------------------------

                """.formatted(
                        history,
                        context,
                        question
                );
    }
}
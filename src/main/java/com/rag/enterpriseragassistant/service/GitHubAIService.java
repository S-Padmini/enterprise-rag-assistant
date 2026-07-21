package com.rag.enterpriseragassistant.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class GitHubAIService {


    @Value("${github.token}")
    private String githubToken;


    @Value("${github.model}")
    private String model;


    private final PromptTemplateService promptTemplateService;


    public GitHubAIService(
            PromptTemplateService promptTemplateService) {

        this.promptTemplateService = promptTemplateService;
    }



    public String generateResponse(
            String context,
            String question,
            String history) {


        try {


            // Step 1: Create RAG prompt
            String prompt =
                    promptTemplateService.buildPrompt(
                            context,
                            question,
                            history
                    );



            // Step 2: Escape JSON characters

            String escapedPrompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "");



            // Step 3: Create API request body

            String jsonRequest = """
                    {
                      "model": "%s",
                      "messages": [
                        {
                          "role": "user",
                          "content": "%s"
                        }
                      ],
                      "temperature": 0.2
                    }
                    """.formatted(
                            model,
                            escapedPrompt
                    );



            // Step 4: Call GitHub AI Model API

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            "https://models.inference.ai.azure.com/chat/completions"
                                    )
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + githubToken
                            )
                            .POST(
                                    HttpRequest.BodyPublishers.ofString(
                                            jsonRequest
                                    )
                            )
                            .build();



            HttpClient client =
                    HttpClient.newHttpClient();



            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );



            // Step 5: Return AI response

            return response.body();



        } catch (Exception e) {


            return "AI Connection Error : " + e.getMessage();

        }
    }
}
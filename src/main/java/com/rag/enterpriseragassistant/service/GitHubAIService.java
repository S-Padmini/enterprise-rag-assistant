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


    public String generateResponse(String context, String question, String history) {

        try {

            String prompt =
                    "You are an Enterprise RAG Assistant.\n\n" +

                    "Use previous conversation history if available.\n\n" +

                    "Conversation History:\n" +
                    history +

                    "\n\nAnswer ONLY using the following document context.\n\n" +

                    "Document Context:\n" +
                    context +

                    "\n\nIf answer is not available:\n" +
                    "I couldn't find that information.\n\n" +

                    "Question:\n" +
                    question;


            // Escape JSON special characters
            String escapedPrompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "");


            String jsonRequest = """
                    {
                      "model": "%s",
                      "messages": [
                        {
                          "role": "user",
                          "content": "%s"
                        }
                      ]
                    }
                    """.formatted(model, escapedPrompt);


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://models.inference.ai.azure.com/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + githubToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();


            HttpClient client = HttpClient.newHttpClient();


            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );


            return response.body();


        } catch (Exception e) {

            return "AI Connection Error : " + e.getMessage();

        }
    }
}
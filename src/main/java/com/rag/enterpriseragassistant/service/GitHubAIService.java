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



    public String generateResponse(String prompt) {


        try {

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
                    """.formatted(model, prompt);



            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                    "https://models.inference.ai.azure.com/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                    "Bearer " + githubToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();



            HttpClient client = HttpClient.newHttpClient();


            HttpResponse<String> response =
                    client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                    );


            return response.body();


        }
        catch(Exception e){

            return "AI Connection Error : " + e.getMessage();

        }

    }

}
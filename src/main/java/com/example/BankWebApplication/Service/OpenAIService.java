package com.example.BankWebApplication.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class OpenAIService {

    private static final String OPENAI_MODEL = "gpt-3.5-turbo"; // Use chat model
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String processCustomerRequest(String customerQuery) throws Exception {
        // ✅ Fix: Use "messages" instead of "prompt"
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", OPENAI_MODEL);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 150);

        // ✅ Correct OpenAI Chat API format (system + user)
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful banking assistant."));
        messages.add(Map.of("role", "user", "content", customerQuery));

        requestBody.put("messages", messages);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Build request entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Execute API request
        ResponseEntity<Map> response = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Unexpected API response: " + response.getStatusCode());
        }

        // ✅ Fix: Correctly parse response from OpenAI API
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                return message.get("content").toString().trim();
            }
        }

        throw new Exception("Invalid response from OpenAI API");
    }
}

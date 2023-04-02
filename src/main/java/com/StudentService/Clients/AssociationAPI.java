package com.StudentService.Clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AssociationAPI {

    public boolean existsBySkill(String skill) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/api/v1/association/VerifySkill";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a map to hold the skill value
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("skill", skill);

        // Pass the map directly to the HttpEntity constructor
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // Use exchange instead of postForObject for better error handling
        boolean response = restTemplate.exchange(url, HttpMethod.POST, entity, Boolean.class).getBody();
        return response;
    }
}

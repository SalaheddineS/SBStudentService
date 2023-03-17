package com.StudentService.Clients;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RESTApi_validation {
    @Value("${api.url}")
    private  String _url;
    public  boolean ValidateAuthentified(String token) {
        RestTemplate _restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Boolean> result = _restTemplate.exchange(_url, HttpMethod.GET, entity, Boolean.class);
            return result.getBody();
        }catch (Exception e){throw new RuntimeException("Error on api call");}

}}

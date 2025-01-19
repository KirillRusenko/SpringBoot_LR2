package org.example.lr02_spring.service;

import org.example.lr02_spring.model.Request;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Qualifier("ModifySourceRequestService")
@Service
public class ModifySourceRequestService implements ModifyRequestService{
    @Override
    public void modify(Request request) {
        request.setSource("Modified source");

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange("http://localhost:8084/feedback", HttpMethod.POST,
                httpEntity, new ParameterizedTypeReference<>() {
                });
    }
}

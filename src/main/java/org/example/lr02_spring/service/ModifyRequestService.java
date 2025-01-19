package org.example.lr02_spring.service;

import org.example.lr02_spring.model.Request;
import org.springframework.stereotype.Service;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}

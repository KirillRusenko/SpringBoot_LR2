package org.example.lr02_spring.service;

import org.example.lr02_spring.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}

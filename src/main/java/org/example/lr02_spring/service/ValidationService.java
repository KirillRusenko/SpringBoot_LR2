package org.example.lr02_spring.service;

import org.example.lr02_spring.exception.UnsupportedCodeException;
import org.example.lr02_spring.exception.ValidationFailedException;
import org.example.lr02_spring.model.Request;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException;

   void isRequestValid(Request request) throws UnsupportedCodeException, ValidationFailedException;
}
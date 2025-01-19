package org.example.lr02_spring.service;

import org.example.lr02_spring.exception.UnsupportedCodeException;
import org.example.lr02_spring.exception.ValidationFailedException;
import org.example.lr02_spring.model.Request;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RequestValidationService implements ValidationService {
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }

    @Override
    public void isRequestValid(Request request) throws UnsupportedCodeException, ValidationFailedException {
        request.validateRequest();
    }
}

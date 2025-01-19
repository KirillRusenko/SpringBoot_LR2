package org.example.lr02_spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.example.lr02_spring.exception.UnsupportedCodeException;
import org.example.lr02_spring.exception.ValidationFailedException;
import org.example.lr02_spring.model.*;
import org.example.lr02_spring.service.ModifyRequestService;
import org.example.lr02_spring.service.ModifyResponseService;
import org.example.lr02_spring.service.ModifySourceRequestService;
import org.example.lr02_spring.service.ValidationService;
import org.example.lr02_spring.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final ModifyRequestService modifySourceRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        @Qualifier("ModifyRequestSystemTimeService") ModifyRequestService modifyRequestService,
                        @Qualifier("ModifySourceRequestService") ModifyRequestService modifySourceRequestService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.modifySourceRequestService = modifySourceRequestService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();


        log.info("response: {}", response);
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("validation exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("unknown exception: {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            validationService.isRequestValid(request);
            }
        catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED_UID);
            log.error("unsupported uid: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_CODE_EXCEPTION);
            response.setErrorMessage(ErrorMessages.valueOf(e.getMessage()));
            log.error("unsupported uid: {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
        modifySourceRequestService.modify(request);
        log.info("request: {}", request);
        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }
}
package com.backend.api.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    public static final String BASE_PATH = "/api/v1/";

    public BaseController() {
    }

    protected ResponseEntity buildSuccessResponseWithNoContent() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity buildCreatedSuccessResponse(Object object) {
        return ResponseEntity.status(HttpStatus.CREATED).body(object);
    }

    protected ResponseEntity buildSuccessResponse(Object object) {
        return ResponseEntity.ok(object);
    }

    protected ResponseEntity buildSuccessResponseAccepted() {
        return ResponseEntity.accepted().build();
    }
}
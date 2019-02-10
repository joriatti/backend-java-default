package com.backend.api.v1.interceptor;

import com.backend.exceptions.BadRequestException;
import com.backend.exceptions.ForbiddenException;
import com.backend.exceptions.InternalServerErrorException;
import com.backend.exceptions.NotFoundException;
import com.backend.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private BuilderErrorResponseEntity builderErrorResponseEntity;

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponse> badRequestException(BadRequestException ex) {
        return builderErrorResponseEntity.build(ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> unauthorizedException(UnauthorizedException ex) {
        return builderErrorResponseEntity.build(ex);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<ErrorResponse> forbiddenException(ForbiddenException ex) {
        return builderErrorResponseEntity.build(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException ex) {
        return builderErrorResponseEntity.build(new ForbiddenException("BACKEND_GENERIC_ACCESS_DENIED", ex.getCause()));
    }


    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> notFoundException(NotFoundException ex) {
        return builderErrorResponseEntity.build(ex);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<ErrorResponse> internalServerErrorException(InternalServerErrorException ex) {
        return builderErrorResponseEntity.build(ex);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> genericException(Exception ex) {
        return builderErrorResponseEntity.build(ex);
    }
}
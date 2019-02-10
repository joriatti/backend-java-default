package com.backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {
    private static final long serialVersionUID = 1L;
    public static final int STATUS = 400;

    public BadRequestException(String code) {
        super(code);
    }

    public BadRequestException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public BadRequestException(String code, Throwable cause) {
        super(code, cause);
    }

    public BadRequestException(String code, Object[] parameters, Throwable cause) {
        super(code, parameters, cause);
    }

    public BadRequestException(String code, List<? extends BaseException> errors) {
        super(code, errors);
    }

    public BadRequestException(String code, List<? extends BaseException> errors, Object[] parameters) {
        super(code, errors, parameters);
    }

    public BadRequestException(String code, List<? extends BaseException> errors, Throwable cause) {
        super(code, errors, cause);
    }

    public BadRequestException(String code, List<? extends BaseException> errors, Throwable cause, Object[] parameters) {
        super(code, errors, cause, parameters);
    }

    public int getStatus() {
        return STATUS;
    }
}
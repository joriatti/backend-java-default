package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends BaseException {
    private static final long serialVersionUID = 1L;
    public static final int STATUS = 500;

    public InternalServerErrorException(String code) {
        super(code);
    }

    public InternalServerErrorException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public InternalServerErrorException(String code, Throwable cause) {
        super(code, cause);
    }

    public InternalServerErrorException(String code, Object[] parameters, Throwable cause) {
        super(code, parameters, cause);
    }

    public int getStatus() {
        return STATUS;
    }
}
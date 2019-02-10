package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {
    private static final long serialVersionUID = 1L;
    public static final int STATUS = 404;

    public NotFoundException(String code) {
        super(code);
    }

    public NotFoundException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public NotFoundException(String code, Throwable cause) {
        super(code, cause);
    }

    public NotFoundException(String code, Object[] parameters, Throwable cause) {
        super(code, parameters, cause);
    }

    public int getStatus() {
        return STATUS;
    }
}
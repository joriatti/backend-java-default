package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends BaseException {
    private static final long serialVersionUID = 1L;
    public static final int STATUS = 401;

    public UnauthorizedException(String code) {
        super(code);
    }

    public UnauthorizedException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public UnauthorizedException(String code, Throwable cause) {
        super(code, cause);
    }

    public UnauthorizedException(String code, Object[] parameters, Throwable cause) {
        super(code, parameters, cause);
    }

    public int getStatus() {
        return STATUS;
    }
}
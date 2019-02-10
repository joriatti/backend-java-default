package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseException {

    private static final long serialVersionUID = 1L;
    public static final int STATUS = 403;

    public ForbiddenException(String code) {
        super(code);
    }

    public ForbiddenException(String code, Object[] parameters) {
        super(code, parameters);
    }

    public ForbiddenException(String code, Throwable cause) {
        super(code, cause);
    }

    public ForbiddenException(String code, Object[] parameters, Throwable cause) {
        super(code, parameters, cause);
    }

    @Override
    public int getStatus() {
        return STATUS;
    }
}
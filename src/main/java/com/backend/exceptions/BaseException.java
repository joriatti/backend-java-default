package com.backend.exceptions;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseException extends Exception {
    private static final long serialVersionUID = 1L;
    private String code;
    private Object[] parameters;
    private List<? extends BaseException> errors;

    public BaseException(String code) {
        super(code);
        this.parameters = new Object[0];
        this.errors = new ArrayList();
        this.code = code;
    }

    public BaseException(String code, Object[] parameters) {
        this(code);
        this.parameters = parameters;
    }

    public BaseException(String code, Throwable cause) {
        super(code, cause);
        this.parameters = new Object[0];
        this.errors = new ArrayList();
        this.code = code;
    }

    public BaseException(String code, Object[] parameters, Throwable cause) {
        this(code, cause);
        this.parameters = parameters;
    }

    public BaseException(String code, List<? extends BaseException> errors) {
        this(code);
        this.errors = errors;
    }

    public BaseException(String code, List<? extends BaseException> errors, Object[] parameters) {
        this(code, errors);
        this.parameters = parameters;
    }

    public BaseException(String code, List<? extends BaseException> errors, Throwable cause) {
        this(code, cause);
        this.errors = errors;
    }

    public BaseException(String code, List<? extends BaseException> errors, Throwable cause, Object[] parameters) {
        this(code, errors, cause);
        this.parameters = parameters;
    }

    public String getCode() {
        return this.code;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public abstract int getStatus();

    public List<? extends BaseException> getErrors() {
        return this.errors;
    }
}
package com.backend.api.v1.interceptor;

import java.util.List;

public class ErrorResponseContentException extends Exception {
    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public ErrorResponseContentException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public int getStatus() {
        return errorResponse.getStatus();
    }

    public String getCode() {
        return errorResponse.getCode();
    }

    @Override
    public String getMessage() {
        return errorResponse.getMessage();
    }

    public List<ErrorResponse> getDetails() {
        return errorResponse.getDetails();
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public String toString() {
        return "ErrorResponseContentException{" +
                "status=" + getStatus() +
                ", code='" + getCode() + '\'' +
                ", message='" + getMessage() + '\'' +
                ", details=" + getDetails() +
                '}';
    }
}
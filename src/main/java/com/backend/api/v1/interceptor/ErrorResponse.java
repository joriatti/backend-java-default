package com.backend.api.v1.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;
    private String code;
    private String message;
    private List<ErrorResponse> details = new ArrayList<>();

    private ErrorResponse(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.details = builder.details;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorResponse> getDetails() {
        return details;
    }

    public void setDetails(List<ErrorResponse> details) {
        this.details = details;
    }

    public static Builder builder(String code, String message) {
        return new Builder(code, message);
    }

    public static class Builder {
        private final String code;
        private final String message;
        private List<ErrorResponse> details = new ArrayList<>();

        private Builder(String code, String message) {
            if ((code == null) || (message == null)) {
                throw new IllegalArgumentException("code, message can not be null.");
            }
            this.code = code;
            this.message = message;
        }

        public Builder details(ErrorResponse errorResponse) {
            this.details.add(errorResponse);
            return this;
        }

        public Builder details(Collection<ErrorResponse> details) {
            this.details.addAll(details);
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", details=" + details +
                '}';
    }
}
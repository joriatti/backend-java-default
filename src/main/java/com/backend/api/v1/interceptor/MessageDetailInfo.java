package com.backend.api.v1.interceptor;

import java.io.Serializable;

public class MessageDetailInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String message;

    public MessageDetailInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
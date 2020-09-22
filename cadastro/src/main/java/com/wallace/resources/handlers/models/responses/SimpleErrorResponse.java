package com.wallace.resources.handlers.models.responses;

import java.io.Serializable;

public class SimpleErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;

    public SimpleErrorResponse(String message) {
        this.message = message;
    }

    public static SimpleErrorResponse of(String message) {
        return new SimpleErrorResponse(message);
    }

    public String getMessage() {
        return message;
    }
}

package com.wallace.resources.handlers.models.responses;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
    private String field;

    public ErrorResponse(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public static ErrorResponse of(String message, String field) {
        return new ErrorResponse(message, field);
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}

package com.wallace.resources.handlers.models.responses;

import java.io.Serializable;
import java.util.List;

public class MultipleErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ErrorResponse> errosList;

    public MultipleErrorResponse(List<ErrorResponse> errosList) {
        this.errosList = errosList;
    }

    public static MultipleErrorResponse of(List<ErrorResponse> simpleErrorResponseList) {

        return new MultipleErrorResponse(simpleErrorResponseList);
    }

    public List<ErrorResponse> getErrosList() {
        return errosList;
    }
}

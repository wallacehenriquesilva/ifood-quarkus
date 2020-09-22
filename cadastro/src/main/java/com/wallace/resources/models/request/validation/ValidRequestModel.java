package com.wallace.resources.models.request.validation;

import javax.validation.ConstraintValidatorContext;

public interface ValidRequestModel {
    default boolean isValid(ConstraintValidatorContext validatorContext) {
        return true;
    }
}

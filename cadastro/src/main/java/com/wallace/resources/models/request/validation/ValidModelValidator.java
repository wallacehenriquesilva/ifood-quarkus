package com.wallace.resources.models.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidModelValidator implements ConstraintValidator<ValidModel, ValidRequestModel> {
    @Override
    public void initialize(ValidModel constraintAnnotation) {

    }

    @Override
    public boolean isValid(ValidRequestModel validRequestModel, ConstraintValidatorContext constraintValidatorContext) {
        return validRequestModel.isValid(constraintValidatorContext);
    }
}

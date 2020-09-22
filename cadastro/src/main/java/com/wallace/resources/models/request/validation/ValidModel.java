package com.wallace.resources.models.request.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidModelValidator.class})
@Documented
public @interface ValidModel {
    String message() default "Erro";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

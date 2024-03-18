package com.sm.cb.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import com.sm.cb.validator.DocTypeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DocTypeValidator.class)
public @interface ValidateDocType {

	String message() default "Invalid document type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

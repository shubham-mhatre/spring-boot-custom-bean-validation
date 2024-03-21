package com.sm.cb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sm.cb.validator.MandatoryValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MandatoryValidator.class)
public @interface ValidateMandatory {
	
	String message() default "field is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

	String errorCode();

}

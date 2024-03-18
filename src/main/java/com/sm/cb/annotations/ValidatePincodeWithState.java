package com.sm.cb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sm.cb.validator.PincodeBelongsToStateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PincodeBelongsToStateValidator.class)
public @interface ValidatePincodeWithState {
	
	String message() default "Pincode does not belong to the specified state";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

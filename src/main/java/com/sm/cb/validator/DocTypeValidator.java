package com.sm.cb.validator;

import com.sm.cb.annotations.ValidateDocType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocTypeValidator implements ConstraintValidator<ValidateDocType, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isEmpty()) {
            setErrorCode(context, "E1000");
            return false;
        }
		boolean isValid = checkAgainstMasterValues(value);

        if (!isValid) {
            setErrorCode(context, "E1001");
        }

        return isValid;
	}
	
	private boolean checkAgainstMasterValues(String docType) {
        return true;
    }
	
	private void setErrorCode(ConstraintValidatorContext context, String errorCode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode)
                .addPropertyNode("errorCode")
                .addConstraintViolation();
    }

}

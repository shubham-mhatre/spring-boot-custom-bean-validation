package com.sm.cb.validator;


import org.springframework.beans.factory.annotation.Autowired;

import com.sm.cb.annotations.ValidateMandatory;
import com.sm.cb.utility.CommonUtility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MandatoryValidator implements ConstraintValidator<ValidateMandatory, String>{

	private String errorCode;
	private final CommonUtility commonUtility;
	@Autowired
    public MandatoryValidator(CommonUtility commonUtility) {
        this.commonUtility = commonUtility;
    }
	
	@Override
    public void initialize(ValidateMandatory constraintAnnotation) {
        this.errorCode = constraintAnnotation.errorCode();
    }
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid=true;
		if(commonUtility.checkMandatory.test(value)) {
			setErrorCode(context, errorCode);
			isValid=false;
		}
		return isValid;
	}
	
	private void setErrorCode(ConstraintValidatorContext context, String errorCode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode)
                .addPropertyNode("errorCode")
                .addConstraintViolation();
    }

}

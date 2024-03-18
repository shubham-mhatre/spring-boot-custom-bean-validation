package com.sm.cb.validator;


import com.sm.cb.annotations.ValidatePincodeWithState;
import com.sm.cb.model.EinvProc;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PincodeBelongsToStateValidator implements ConstraintValidator<ValidatePincodeWithState, EinvProc>{

	@Override
	public boolean isValid(EinvProc einvProc, ConstraintValidatorContext context) {
		if ( einvProc.getPincode() == null || einvProc.getPincode().isEmpty() || einvProc.getStateCode()== null || einvProc.getStateCode().isEmpty()) {
           return true;
        }
		boolean isValid = checkAgainstMasterValues(einvProc.getStateCode(),einvProc.getPincode());
		if (!isValid) {
            setErrorCode(context, "E1003");
        }
		return isValid;
	}
	
	private boolean checkAgainstMasterValues(String stateCode,String pincode) {
        return false;
    }
	
	private void setErrorCode(ConstraintValidatorContext context, String errorCode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode)
                .addPropertyNode("errorCode")
                .addConstraintViolation();
    }

}

package com.sm.cb.controller.service;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.cb.model.EinvProc;
import com.sm.cb.repositories.EinvProcRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class RuleService {
	
	private final EinvProcRepository einvProcRepository;
	@Autowired
    public RuleService(EinvProcRepository einvProcRepository) {
        this.einvProcRepository = einvProcRepository;
    }
	
	@Autowired
    private Validator validator;

	private List<EinvProc> getPendingValidationDataByBatchNo(String batchNo){
		return einvProcRepository.findByUploadBatchNo(batchNo);
	}

	public String validateData(String data) {
		JSONObject jsonObj=new JSONObject(data);
		String batchNo=jsonObj.getString("batch_no");
		List<EinvProc> pendingForValidation=getPendingValidationDataByBatchNo(batchNo);
		
		pendingForValidation.forEach(proc->{
			Set<ConstraintViolation<EinvProc>> violations = validator.validate(proc);
			System.out.println(violations);
			if (!violations.isEmpty()) {
	            StringBuilder errorCodes = new StringBuilder();
	            violations.forEach(violation -> {
	                errorCodes.append(violation.getMessage()).append(",");
	            });

	            String errorCodeList = errorCodes.substring(0, errorCodes.length() - 1);
	            proc.setErrorCode(errorCodeList);
	            proc.setValidationStatus(-1);
	            //einvProcRepository.save(proc);
	         }else {
	            proc.setErrorCode(null);
	            proc.setValidationStatus(1); 
	            // einvProcRepository.save(proc);
	        }
		});
		
		
		
		return "validation run for batch no : "+batchNo;
	}
}

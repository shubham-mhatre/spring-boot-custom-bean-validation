package com.sm.cb.controller.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sm.cb.model.EinvProc;
import com.sm.cb.repositories.EinvProcRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class RuleService {
	
	 @Value("${async_required}")
	 private String asyncRequired;
	
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
		
		Instant startTime = Instant.now();
		
		if("Y".equalsIgnoreCase(asyncRequired)) {
			executeflowInAsync(pendingForValidation);
		}else {
			executeflowInSync(pendingForValidation);			
		}	
		
		Instant endTime = Instant.now();
	    Duration duration = Duration.between(startTime, endTime);
	    long totalTimeMillis = duration.toMillis();
	    long totalTimeSeconds = duration.toSeconds();
		
	    return " for batch no : " + batchNo + "\nTotal time taken millis: " + totalTimeMillis + " ms "
	    		+ "\nTotal time taken seconds:"+totalTimeSeconds ;
	}
	
	private void executeflowInSync(List<EinvProc> pendingForValidation) {
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
	}

	private void executeflowInAsync(List<EinvProc> pendingForValidation) {
		Executor customExecutor = Executors.newFixedThreadPool(2);
		List<CompletableFuture<Void>> validationFutures = pendingForValidation.stream()
				.map(proc -> validateAsync(proc, customExecutor))
	            .collect(Collectors.toList());

	    CompletableFuture<Void> allOf = CompletableFuture.allOf(
	            validationFutures.toArray(CompletableFuture[]::new));	    
	    allOf.join();		
	}

	private CompletableFuture<Void> validateAsync(EinvProc proc,Executor executor) {
	    return CompletableFuture.runAsync(() -> {
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
	        } else {
	            proc.setErrorCode(null);
	            proc.setValidationStatus(1);
	            // einvProcRepository.save(proc);
	        }
	    },executor);
	}
}

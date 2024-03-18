package com.sm.cb.model;

import com.sm.cb.annotations.ValidateDocType;
import com.sm.cb.annotations.ValidatePincodeWithState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "einv_proc")
@ValidatePincodeWithState
public class EinvProc {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "doc_no")
    private String docNo;

    @ValidateDocType
    @Column(name = "doc_type")
    private String docType;

    @Column(name = "pincode")
    private String pincode;
    
    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "validation_status")
    private Integer validationStatus;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "upload_batch_no")
    private String uploadBatchNo;

//    public String setValErrorCode(String val){
//		if(val == null || "".equals(val)) {
//			return "";
//    	}
//		else{
//			return val+",";
//		}
//    }
}

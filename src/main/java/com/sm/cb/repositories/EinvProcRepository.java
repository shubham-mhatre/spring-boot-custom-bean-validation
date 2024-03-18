package com.sm.cb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sm.cb.model.EinvProc;

@Repository
public interface EinvProcRepository extends JpaRepository<EinvProc, Integer>{

	public List<EinvProc> findByUploadBatchNo(String uploadBatchNo);
}

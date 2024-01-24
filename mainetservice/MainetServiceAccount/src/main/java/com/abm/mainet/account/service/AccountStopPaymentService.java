package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.StopPaymentDTO;

public interface AccountStopPaymentService {
	
	 public List<StopPaymentDTO> findByAllGridstopPaymentData(Long bankAccount, Date fromDte,
	            Date toDte, Long orgId,Long chequeStatus);
	 
	 public List<StopPaymentDTO> findRecordsForStopPayment(Long bankAccount, Date fromDte,
	            Date toDte, Long orgId);
	 
	 StopPaymentDTO savestopPaymentFormData(StopPaymentDTO stopPaymentDTO);
}

package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.ChequeIssueanceDTO;

/**
 * 
 * @author vishwanath.s
 *
 */
public interface AccountChequeIssueanceService {
	
 public List<ChequeIssueanceDTO> findByAllGridChequeNotIssuanceData(Long bankAccount, Date fromDte,
            Date toDte, Long orgId,Long chequeStatus);
 
 ChequeIssueanceDTO saveChequeIssueanceFormData(ChequeIssueanceDTO chequeIssueanceDto);
 

}

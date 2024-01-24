package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.InsuranceClaim;
import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;

public interface IInsuranceClaimdao {
	
	 public List<InsuranceClaim> searchInsuranceClaim(Long department, Long vehicleType, Long veid, Long orgid);
	  public List<InsuranceClaim> insuranceClaim(Date issueDate, Date endDate, Long veid, Long orgid);

}

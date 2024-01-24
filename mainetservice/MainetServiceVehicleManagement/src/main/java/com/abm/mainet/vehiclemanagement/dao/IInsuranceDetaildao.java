package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.InsuranceDetail;

public interface IInsuranceDetaildao {

  public List<InsuranceDetail> searchInsuranceDetails(Long department, Long vehicleType, Long veid, Long orgid);
  
  public List<InsuranceDetail> insuranceDetails(Date issueDate, Date endDate, Long veid, Long orgid);

}

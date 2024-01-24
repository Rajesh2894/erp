package com.abm.mainet.bill.service;

import com.abm.mainet.common.dto.CommonChallanDTO;

@FunctionalInterface
public interface BillDetailsService {
	
	public CommonChallanDTO getBillDetails(CommonChallanDTO commonChallanDTO);
	
	public default Long getCountByApplNoOrLegacyNo(String applNo, String legacyApplNo, Long orgId) {
		
		return null;
	}

}

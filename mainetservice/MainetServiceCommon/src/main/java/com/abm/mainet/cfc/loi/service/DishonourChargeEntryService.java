package com.abm.mainet.cfc.loi.service;

import java.math.BigDecimal;
import java.util.Map;

public interface DishonourChargeEntryService {
	public Boolean saveDishonourCharge(Long appId,Long orgId,Long empId,BigDecimal amt,String remark);

	Boolean updateModuleWiseStatus(Long applicationId, Long serviceId, String serviceShortDesc, long orgId)
			throws ClassNotFoundException, LinkageError;
}

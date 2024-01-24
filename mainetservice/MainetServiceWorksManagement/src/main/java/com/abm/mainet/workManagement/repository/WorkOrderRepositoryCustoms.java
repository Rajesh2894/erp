package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.WorkOrder;

public interface WorkOrderRepositoryCustoms {

	/**
	 * used to get filter work order List by different parameters.
	 * 
	 * @param workOrderNo
	 * @param workOrderDate
	 * @param workOrderDate
	 * @param agreementFormDate
	 * @param agreementToDate
	 * @param vendorName
	 * @param orgId 
	 * @return
	 */
	List<WorkOrder> getFilterWorkOrderGenerationList(String workOrderNo, Date workOrderDate, Date contractFromDate,
			Date contractToDate, String vendorName, Long orgId);

}

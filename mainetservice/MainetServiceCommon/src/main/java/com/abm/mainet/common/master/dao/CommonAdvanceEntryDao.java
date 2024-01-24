package com.abm.mainet.common.master.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.AdvanceRequisition;

public interface CommonAdvanceEntryDao {

	List<AdvanceRequisition> getFilterRequisition(Date advanceEntryDate, Long vendorId, Long deptId, Long orgId);

}

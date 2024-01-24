package com.abm.mainet.firemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.firemanagement.domain.FmPetrolRequisition;

public interface IPetrolRequisitionDAO {

	List<FmPetrolRequisition> searchPetrolRequestForm(Date fromDate, Date toDate, Long deptId, String veNo, Long orgid);

	List<FmPetrolRequisition> getDetailByVehNo(Long complainNo, Long orgid);

}

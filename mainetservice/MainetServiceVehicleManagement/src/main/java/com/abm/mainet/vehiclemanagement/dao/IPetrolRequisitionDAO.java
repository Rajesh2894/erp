package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.PetrolRequisitionDetails;

public interface IPetrolRequisitionDAO {

	List<PetrolRequisitionDetails> searchPetrolRequestForm(Date fromDate, Date toDate, Long deptId, Long veNo, Long orgid);

	List<PetrolRequisitionDetails> getDetailByVehNo(String complainNo, Long orgid);

}

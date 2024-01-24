package com.abm.mainet.vehiclemanagement.dao;

import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.OEMWarranty;

public interface IOEMWarrantyServiceDAO {

	public List<OEMWarranty> searchOemWarrantyDetails(Long department, Long vehicleType, Long veNo, Long orgid);

}

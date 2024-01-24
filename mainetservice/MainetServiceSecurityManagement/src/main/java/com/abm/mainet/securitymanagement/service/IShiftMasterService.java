package com.abm.mainet.securitymanagement.service;

import java.util.List;

import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.securitymanagement.dto.ShiftMasterDTO;

public interface IShiftMasterService {

	public ShiftMasterDTO saveOrUpdate(ShiftMasterDTO dto);
	
	public List<ShiftMasterDTO> searchShift(Long shiftId,Long orgid);
	
	public ShiftMasterDTO findById(Long shiftMasId);

	List<LookUp> getAvtiveShift(Long orgId);

}

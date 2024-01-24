package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import com.abm.mainet.securitymanagement.domain.ShiftMaster;

public interface IShiftMasterDao {
	
	public List<ShiftMaster> searchShift(Long shiftId,Long orgid);
}

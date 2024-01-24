package com.abm.mainet.rts.dao;

import java.util.List;

import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;

public interface IrtsExternalDAO {

	public List<Object[]> getIntegratedServiceApplications(CitizenDashBoardReqDTO request);
}

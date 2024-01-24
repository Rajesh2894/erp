package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CFCApplicationStatusDto;

public interface ICFCApplicationStatusDao {

	public List<TbCfcApplicationMstEntity> getCFCApplicationEtites(CFCApplicationStatusDto cfcApplicationStatusDto);
}

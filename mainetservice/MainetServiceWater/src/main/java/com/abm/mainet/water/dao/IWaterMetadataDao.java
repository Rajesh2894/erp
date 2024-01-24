package com.abm.mainet.water.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.water.domain.TbKCsmrInfoMH;

public interface IWaterMetadataDao {

	public List<TbKCsmrInfoMH> getWaterDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);
}

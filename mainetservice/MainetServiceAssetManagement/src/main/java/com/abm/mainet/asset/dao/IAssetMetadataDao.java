package com.abm.mainet.asset.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.asset.domain.AssetInformation;

public interface IAssetMetadataDao {

	public List<AssetInformation> getAssetDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);
}

package com.abm.mainet.tradeLicense.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;

public interface TradeMeatedataDao {

	public List<TbMlTradeMast> getTradeDetail(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap);
}

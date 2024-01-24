package com.abm.mainet.tradeLicense.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;

@Repository
public class TradeMetadataDaoImpl extends AbstractDAO<Long> implements TradeMeatedataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbMlTradeMast> getTradeDetail(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		TbMlTradeMast trade = new TbMlTradeMast();

		boolean flag = false;
		boolean argumentFlag = false;
		try {
			String json = Utility.getMapper().writeValueAsString(trade);
			Map<String, Object> map = null;
			map = Utility.getMapper().readValue(json, Map.class);
			builder.append(" from TbMlTradeMast t where t.orgid=:orgId ");
			if (colname != null) {
				for (String mapNew : map.keySet()) {
					if (colname.equalsIgnoreCase(mapNew) && (Colvalue!=null && !Colvalue.isEmpty())) {
						flag = true;
						builder.append(" and t." + mapNew + " like :anyValue");
						break;
					}
				}
			}
			if (MapUtils.isNotEmpty(argumentsMap)) {
				argumentFlag = true;
				if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
					builder.append(" and t.trdWard1=:trdWard1 ");
				}
				if (argumentsMap.get("ward") != null && !argumentsMap.get("ward").toString().equals("0")) {
					builder.append(" and t.trdWard2=:trdWard2 ");
				}
			}

			Query query = this.createQuery(builder.toString());
			if (orgId != null) {
				query.setParameter("orgId", orgId);
			}
			if (flag == true || argumentFlag == true) {
				if (Colvalue != null && flag == true && !Colvalue.isEmpty()) {
					query.setParameter("anyValue", '%' + Colvalue + '%');
				}
				if (argumentFlag == true) {
					if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
						query.setParameter("trdWard1", Long.parseLong(argumentsMap.get("zone").toString()));
					}
					if (argumentsMap.get("ward") != null && !argumentsMap.get("ward").toString().equals("0")) {
						query.setParameter("trdWard2", Long.parseLong(argumentsMap.get("ward").toString()));
					}
				}
			}
			List<TbMlTradeMast> tradeList = query.getResultList();
			return tradeList;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		// return null;
	}

}

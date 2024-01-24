package com.abm.mainet.water.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

@Repository
public class WaterMetadataDaoImpl extends AbstractDAO<Long> implements IWaterMetadataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbKCsmrInfoMH> getWaterDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		TbKCsmrInfoMH waterEntity = new TbKCsmrInfoMH();

		boolean flag = false;
		boolean argumentFlag = false;
		try {
			String json = Utility.getMapper().writeValueAsString(waterEntity);
			Map<String, Object> map = null;
			map = Utility.getMapper().readValue(json, Map.class);
			builder.append(" FROM TbKCsmrInfoMH th where th.orgId=:orgId ");
			if (colname != null) {
				for (String mapNew : map.keySet()) {
					if (colname.equalsIgnoreCase(mapNew) && (Colvalue!=null && !Colvalue.isEmpty())) {
						flag = true;
						builder.append(" and th." + mapNew + " like :anyValue");
						break;
					}
				}
			}
			if (MapUtils.isNotEmpty(argumentsMap)) {
				argumentFlag = true;
				if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
					builder.append(" and th.codDwzid1=:codDwzid1 ");
				}
				if (argumentsMap.get("ward") != null && !argumentsMap.get("ward").toString().equals("0")) {
					builder.append(" and th.codDwzid2=:codDwzid2 ");
				}
				if (argumentsMap.get("mohalla") != null && !argumentsMap.get("mohalla").toString().equals("0")) {
					builder.append(" and th.codDwzid3=:codDwzid3 ");
				}
			}

			Query query = entityManager.createQuery(builder.toString());
			if (orgId != null) {
				query.setParameter("orgId", orgId);
			}
			if (flag == true || argumentFlag == true) {
				if (Colvalue != null && flag == true && !Colvalue.isEmpty()) {
					query.setParameter("anyValue", '%' + Colvalue + '%');
				}
				if (argumentFlag == true) {
					if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
						query.setParameter("codDwzid1", Long.parseLong(argumentsMap.get("zone").toString()));
					}
					if (argumentsMap.get("ward") != null && !argumentsMap.get("ward").toString().equals("0")) {
						query.setParameter("codDwzid2", Long.parseLong(argumentsMap.get("ward").toString()));
					}
					if (argumentsMap.get("mohalla") != null && !argumentsMap.get("mohalla").toString().equals("0")) {
						query.setParameter("codDwzid3", Long.parseLong(argumentsMap.get("mohalla").toString()));
					}
				}
			}
			List<TbKCsmrInfoMH> assessmentList = query.getResultList();
			return assessmentList;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

}

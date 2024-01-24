package com.abm.mainet.care.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;

@Repository
public class ComplaintRequestDAOImpl extends AbstractDAO<Long> implements ComplaintRequestDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<CareRequest> getComplaintRegisterDetail(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		CareRequest care = new CareRequest();
		boolean argumentFlag = false;
		boolean flag = false;
		try {
			String json = Utility.getMapper().writeValueAsString(care);
			Map<String, Object> map = null;
			map = Utility.getMapper().readValue(json, Map.class);
			builder.append("from CareRequest c where c.orgId=:orgId ");
			if (colname != null) {
				for (String mapNew : map.keySet()) {
					if (colname.equalsIgnoreCase(mapNew) && (Colvalue!=null && !Colvalue.isEmpty())) {
						flag = true;
						builder.append(" and c." + mapNew + " like :anyValue");
						break;
					}
				}
			}
			if (MapUtils.isNotEmpty(argumentsMap)) {
				argumentFlag = true;
				if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
					builder.append(" and c.ward1=:ward1 ");
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
				if (argumentsMap.get("zone") != null && argumentFlag == true
						&& !argumentsMap.get("zone").toString().equals("0")) {
					query.setParameter("ward1", Long.parseLong(argumentsMap.get("zone").toString()));
				}
			}

			List<CareRequest> complaintRegisterList = query.getResultList();
			return complaintRegisterList;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		// return null;
	}
}

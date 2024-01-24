package com.abm.mainet.property.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;

@Repository
public class PropertyMetadataDaoImpl extends AbstractDAO<Long> implements IPropertyMetadataDao {

	public static final Logger LOGGER = Logger.getLogger(PropertyMetadataDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<AssesmentMastEntity> getPropertyDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		AssesmentMastEntity assessEntity = new AssesmentMastEntity();
		AssesmentOwnerDtlEntity ownerEntity = new AssesmentOwnerDtlEntity();
		boolean flag = false;
		boolean argumentFlag = false;
		try {
			String json = Utility.getMapper().writeValueAsString(assessEntity);
			Map<String, Object> map = null;
			String jsonOwner = Utility.getMapper().writeValueAsString(ownerEntity);
			Map<String, Object> mapOwner = null;
			mapOwner = Utility.getMapper().readValue(jsonOwner, Map.class);
			map = Utility.getMapper().readValue(json, Map.class);
			builder.append(" FROM AssesmentMastEntity th where th.orgId=:orgId ");
			if (colname != null) {
				for (String mapNew : map.keySet()) {
					if (colname.equalsIgnoreCase(mapNew) && (Colvalue != null && !Colvalue.isEmpty())) {
						flag = true;
						//builder.append(" and th." + mapNew + " like :anyValue");
						builder.append(" and th." + mapNew + "=:anyValue");
						break;
					}
				}
				if (flag != true) {
					for (String mapNewOwner : mapOwner.keySet()) {
						if (colname.equalsIgnoreCase(mapNewOwner) && (Colvalue != null && !Colvalue.isEmpty())) {
							flag = true;
							builder.append(
									" and th.proAssId in ( select ow.mnAssId.proAssId FROM AssesmentOwnerDtlEntity ow where ow.orgId=:orgId and ow."
											+ mapNewOwner + " like :anyValue and ow.assoOType='P' )");
							break;
						}
					}
				}
			}
			if (MapUtils.isNotEmpty(argumentsMap)) {
				argumentFlag = true;
				if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
					builder.append(" and th.assWard1=:assWard1 ");
				}
				if (argumentsMap.get("ward") != null && !argumentsMap.get("ward").toString().equals("0")) {
					builder.append(" and th.assWard2=:assWard2 ");
				}
				if (argumentsMap.get("mohalla") != null && !argumentsMap.get("mohalla").toString().equals("0")) {
					builder.append(" and th.assWard3=:assWard3 ");
				}
			}

			Query query = entityManager.createQuery(builder.toString());
			if (orgId != null) {
				query.setParameter("orgId", orgId);
			}
			if (flag == true || argumentFlag == true) {
				if (Colvalue != null && flag == true && !Colvalue.isEmpty()) {
					//query.setParameter("anyValue", '%' + Colvalue + '%');
					query.setParameter("anyValue", Colvalue);
				}
				if (argumentFlag == true) {
					if (argumentsMap.get("zone") != null && !argumentsMap.get("zone").toString().equals("0")) {
						query.setParameter("assWard1", Long.parseLong(argumentsMap.get("zone").toString()));
					}
					if (argumentsMap.get("ward") != null && !argumentsMap.get("ward").toString().equals("0")) {
						query.setParameter("assWard2", Long.parseLong(argumentsMap.get("ward").toString()));
					}
					if (argumentsMap.get("mohalla") != null && !argumentsMap.get("mohalla").toString().equals("0")) {
						query.setParameter("assWard3", Long.parseLong(argumentsMap.get("mohalla").toString()));
					}
				}
			}
			List<AssesmentMastEntity> assessmentList = query.getResultList();
			return assessmentList;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		// return null;
	}

}

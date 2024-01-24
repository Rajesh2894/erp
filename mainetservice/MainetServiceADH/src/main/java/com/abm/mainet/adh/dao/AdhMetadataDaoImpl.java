package com.abm.mainet.adh.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;

@Repository
public class AdhMetadataDaoImpl extends AbstractDAO<Long> implements IAdhMetadataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractMastEntity> getAdhDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		ContractMastEntity adhEntity = new ContractMastEntity();
		ContractDetailEntity adhDetailEntity = new ContractDetailEntity();
		boolean flag = false;

		try {
			String json = Utility.getMapper().writeValueAsString(adhEntity);
			Map<String, Object> map = Utility.getMapper().readValue(json, Map.class);
			
			String detailJson = Utility.getMapper().writeValueAsString(adhDetailEntity);
			Map<String, Object> detailsMap = Utility.getMapper().readValue(detailJson, Map.class);
			
			builder.append(" FROM ContractMastEntity ad where ad.orgId=:orgId ");
			if (colname != null) {
				for (String mapNew : map.keySet()) {
					if (colname.equalsIgnoreCase(mapNew) && (Colvalue != null && !Colvalue.isEmpty())) {
						flag = true;
						builder.append(" and CAST( ad." + mapNew + " as string ) like :anyValue");
						break;
					}
				}
				
				if (flag != true) {
					for (String detailMap : detailsMap.keySet()) {
						if (colname.equalsIgnoreCase(detailMap) && (Colvalue != null && !Colvalue.isEmpty())) {
							flag = true;
							builder.append(
									" and ad.contId in ( select ow.contId.contId FROM ContractDetailEntity ow where ow.orgId=:orgId and CAST( ow."
											+ detailMap + " as string ) like :anyValue  )");
							break;
						}
					}
				}
			}

			Query query = entityManager.createQuery(builder.toString());
			if (orgId != null) {
				query.setParameter("orgId", orgId);
			}
	
			if (Colvalue != null && !Colvalue.isEmpty() && flag == true) {
				query.setParameter("anyValue", '%' + Colvalue + '%');
			}

			return query.getResultList();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

}

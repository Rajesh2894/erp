package com.abm.mainet.rnl.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.domain.EstatePropertyEntity;

@Repository
public class RnlMetadataDaoImpl extends AbstractDAO<Long> implements IRnlMetadataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EstatePropertyEntity> getRnlDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		EstatePropertyEntity rnlEntity = new EstatePropertyEntity();

		boolean flag = false;

		try {
			String json = Utility.getMapper().writeValueAsString(rnlEntity);
			Map<String, Object> map = null;
			map = Utility.getMapper().readValue(json, Map.class);
			builder.append(" FROM EstatePropertyEntity ad where ad.orgId=:orgId ");
			if (colname != null) {
				for (String mapNew : map.keySet()) {
					if (colname.equalsIgnoreCase(mapNew) && (Colvalue != null && !Colvalue.isEmpty())) {
						flag = true;
						builder.append(" and ad." + mapNew + " like :anyValue");
						break;
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
			/* if (Colvalue != null && !Colvalue.isEmpty() && flag == true) {
				if (Colvalue.matches("^[0-9]*$")) {           // checks whether given value contains numbers
					query.setParameter("anyValue", '%' + Integer.valueOf(Colvalue) + '%');
				} else {
					query.setParameter("anyValue", '%' + Colvalue + '%');
				}
			} */

			List<EstatePropertyEntity> result = query.getResultList();
			return result;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

}

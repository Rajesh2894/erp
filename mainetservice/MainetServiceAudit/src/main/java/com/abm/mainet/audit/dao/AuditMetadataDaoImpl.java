package com.abm.mainet.audit.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.audit.domain.AuditParaEntryEntity;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;

@Repository
public class AuditMetadataDaoImpl extends AbstractDAO<Long> implements IAuditMetadataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditParaEntryEntity> getAuditDetails(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		StringBuilder builder = new StringBuilder();
		AuditParaEntryEntity auditEntity = new AuditParaEntryEntity();

		boolean flag = false;

		try {
			String json = Utility.getMapper().writeValueAsString(auditEntity);
			Map<String, Object> map = null;
			map = Utility.getMapper().readValue(json, Map.class);
			builder.append(" FROM AuditParaEntryEntity ad where ad.orgId=:orgId ");
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

			if (Colvalue != null  && !Colvalue.isEmpty() && flag == true) {
				query.setParameter("anyValue", '%' + Colvalue + '%');
			}

			List<AuditParaEntryEntity> assessmentList = query.getResultList();
			return assessmentList;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

}

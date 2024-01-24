package com.abm.mainet.audit.dao;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.audit.domain.AuditParaEntryEntity;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

@Repository
public class AuditParaEntryDaoImpl extends AbstractDAO<Long> implements IAuditParaEntryDao  {

	@SuppressWarnings("unchecked")
	public List<AuditParaEntryEntity> searchAuditParaEntry(Long auditType, Long auditDeptId, Long orgId, Long auditParaZone, Long auditParaStatus, String auditParaCode, Date fromDate, Date toDate)
	{
		
		List<AuditParaEntryEntity> auditParaEntryEntityList = null;
		
		try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT p FROM AuditParaEntryEntity p  where p.orgId = :orgid ");

            if (Optional.ofNullable(auditType).orElse(0L) != 0) {
                jpaQuery.append(" and p.auditType = :auditType");
            }
                
            if (Optional.ofNullable(auditDeptId).orElse(0L) != 0) {
                    jpaQuery.append(" and p.auditDeptId = :auditDeptId");
            }
            
            if (Optional.ofNullable(auditParaZone).orElse(0L) != 0) {
                jpaQuery.append(" and p.auditWard1 = :auditParaZone");

            }
            
            if (Optional.ofNullable(auditParaStatus).orElse(0L) != 0) {
                jpaQuery.append(" and p.auditParaStatus = :auditParaStatus");

            }
            
            if (auditParaCode != null) {
                jpaQuery.append(" and p.auditParaCode like :auditParaCode");

            }
            
            if (fromDate != null) {
                jpaQuery.append(" AND p.auditEntryDate >= :fromDate ");
            }
            if (toDate != null) {
                jpaQuery.append(" AND p.auditEntryDate <= :toDate ");
            }
            
            jpaQuery.append(" order by auditType ");
            
            final Query hqlQuery = createQuery(jpaQuery.toString());
            hqlQuery.setParameter("orgid", orgId);
            
            if (Optional.ofNullable(auditType).orElse(0L) != 0) {
            	hqlQuery.setParameter("auditType", auditType);
            }
                
            if (Optional.ofNullable(auditDeptId).orElse(0L) != 0) {
            	hqlQuery.setParameter("auditDeptId", auditDeptId);
            }
            
            if (Optional.ofNullable(auditParaZone).orElse(0L) != 0) {
            	hqlQuery.setParameter("auditParaZone", auditParaZone);
            }
            
            if (Optional.ofNullable(auditParaStatus).orElse(0L) != 0) {
            	hqlQuery.setParameter("auditParaStatus", auditParaStatus);
            }
            
            if (auditParaCode != null) {
            	hqlQuery.setParameter("auditParaCode", "%" + auditParaCode + "%");

            }
            
            if (fromDate != null) {
                hqlQuery.setParameter("fromDate", fromDate);
            }

            if (toDate != null) {
                hqlQuery.setParameter("toDate", toDate);
            }
          
          
            
            auditParaEntryEntityList = hqlQuery.getResultList();


        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Reord", exception);
        }
		
		return auditParaEntryEntityList;
		
	}

	

	
	


}

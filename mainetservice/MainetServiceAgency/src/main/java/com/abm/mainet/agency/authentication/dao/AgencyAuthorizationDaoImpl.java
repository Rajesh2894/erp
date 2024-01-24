package com.abm.mainet.agency.authentication.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;

/**
 * @author Arun.Chavda
 *
 */

@Repository
public class AgencyAuthorizationDaoImpl extends AbstractDAO<Employee> implements AgencyAuthorizationDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.agency.dto.AgencyAuthorizationDao#getAgencyAttachmentsByRowId(java.lang.Long)
     */
    @Override
    public List<CFCAttachment> getAgencyAttachmentsByRowId(final Long rowId, final long orgId) {
        final StringBuilder queryAppender = new StringBuilder();
        queryAppender.append("Select cfc from CFCAttachment cfc  where cfc.orgid = ?1 and cfc.userId=?2 ");

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, orgId);
        query.setParameter(2, rowId);
        @SuppressWarnings("unchecked")
        final List<CFCAttachment> attachments = query.getResultList();
        return attachments;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.web.authentication.agency.dto.AgencyAuthorizationDao#saveAgencyCFCAttachment(com.abm.mainetservice.
     * core.entity.CFCAttachment)
     */
    @Override
    public boolean saveAgencyCFCAttachment(final CFCAttachment attachments) {

        boolean status = false;
        entityManager.merge(attachments);
        if (null != attachments) {
            status = true;
        }
        return status;
    }
}

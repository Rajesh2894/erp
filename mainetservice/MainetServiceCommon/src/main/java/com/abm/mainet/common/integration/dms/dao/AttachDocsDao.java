/**
 *
 */
package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;

/**
 * @author ritesh.patil
 *
 */
@Repository
public class AttachDocsDao extends AbstractDAO<AttachDocs> implements IAttachDocsDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.authentication.role.dao.IEntitlementDao# updateRecord(java.lang.String, java.lang.Long)
     */
    private static Logger logger = Logger.getLogger(AttachDocsDao.class);

    @Override
    @Transactional
    public boolean updateRecord(final List<Long> ids, final Long empId, final String flag) {
        logger.info("updateRecord(List<Long> ids,Long empId,String flag excecution starts ");
        final String str = new String(
                "UPDATE AttachDocs a SET a.status = ?1,a.updatedBy = ?2,a.updatedDate = CURRENT_DATE where a.attId in :attList");
        Query query = null;
        query = createQuery(str);
        query.setParameter(1, flag);
        query.setParameter(2, empId);
        query.setParameter("attList", ids);
        query.executeUpdate();
        logger.info("updateRecord(List<Long> ids,Long empId,String flag excecution ends ");
        return true;

    }

}

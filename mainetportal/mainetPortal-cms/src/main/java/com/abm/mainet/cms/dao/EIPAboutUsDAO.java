package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Bhavesh.Gadhe
 */
@Repository
public class EIPAboutUsDAO extends AbstractDAO<EIPAboutUs> implements IEIPAboutUsDAO {

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPAboutUsDAO#getAboutUs(com.abm.mainet.domain.core.Organisation, java.lang.String)
     */
    @Override
    public EIPAboutUs getAboutUs(final Organisation organisation, final String isDeleted) {

        final Query query = createQuery(
                "select e from EIPAboutUs e where e.orgId = ?1 and e.isDeleted = ?2 order by e.updatedDate desc");
        query.setParameter(1, organisation);
        query.setParameter(2, isDeleted);
        @SuppressWarnings("unchecked")
        final List<EIPAboutUs> list = query.getResultList();

        if ((list == null) || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEIPAboutUsDAO#saveOrUpdateAboutUs(com.abm.mainet.eip.domain.core.EIPAboutUs)
     */
    @Override
    public void saveOrUpdateAboutUs(EIPAboutUs aboutUs, String chekkerflag) {

        EIPAboutUsHistory eipAboutUsHistory = new EIPAboutUsHistory();

        if (aboutUs.getChekkerflag() != null) {
            eipAboutUsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            if(chekkerflag.equalsIgnoreCase("Y")) {
            	aboutUs = entityManager.merge(aboutUs);
            }else {
            	if(aboutUs.getChekkerflag()!=null && !aboutUs.getChekkerflag().isEmpty()) {
            	aboutUs.setChekkerflag("N");
            	}
                aboutUs = entityManager.merge(aboutUs);
            }
        }
        if (aboutUs.getChekkerflag() == null) {
            aboutUs.setAboutUsId(0L);
            eipAboutUsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            entityManager.persist(aboutUs);
        }

        auditService.createHistory(aboutUs, eipAboutUsHistory);

    }

    @SuppressWarnings("unchecked")
	@Override
    public EIPAboutUsHistory getGuestAboutUs(final Organisation organisation,
            final String isDeleted) {
    	List<EIPAboutUsHistory> list=new ArrayList<>();
    	/* order by h.updatedDate desc*/
        final Query query = createQuery(
                "select e.aboutUsId from EIPAboutUs e where e.orgId = ?1 and e.isDeleted = ?2 order by e.updatedDate desc");
        query.setParameter(1, organisation);
        query.setParameter(2, isDeleted);
        @SuppressWarnings("unchecked")
        final List<Long> aboutUsIdList = query.getResultList();
        
        if(aboutUsIdList!=null && !aboutUsIdList.isEmpty()) {
        	final Query query2 = createQuery(
                    "select h from EIPAboutUsHistory h where h.chekkerflag='Y' and h.orgId=?1 and h.aboutUsId in ?2 and (h.aboutUsId,h.updatedDate) in (select eh.aboutUsId,max(eh.updatedDate) from EIPAboutUsHistory eh where eh.chekkerflag='Y' and eh.orgId=?1 and eh.aboutUsId in ?2 group by eh.aboutUsId) order by h.updatedDate desc");
        	query2.setParameter(1, organisation);
        	query2.setParameter(2, aboutUsIdList);
            list = query2.getResultList();
        }
        	
        if ((list == null) || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}

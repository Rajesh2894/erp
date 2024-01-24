/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IPublicNoticesDAO;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 */
@Service
public class AdminPublicNoticesService implements IAdminPublicNoticesService, Serializable {

    private static final long serialVersionUID = 2145950281022218233L;

    @Autowired
    IPublicNoticesDAO publicNoticesDAO;

    @Override
    @Transactional
    public List<PublicNotices> getAllPublicNotices(final Organisation organisation, String flag) {
        return publicNoticesDAO.getAdminPublicNotices(organisation, flag);
    }

    @Override
    @Transactional
    public List<PublicNotices> getCitizenPublicNotices(final Organisation organisation) {
        return publicNoticesDAO.getPublicNotices(organisation);
    }

    @Override
    @Transactional
    public PublicNotices getPublicNotices(final long pnId) {
        return publicNoticesDAO.getpublicNoticesById(pnId);
    }

    @Override
    @Transactional
    public boolean delete(final long pnId) {
        final PublicNotices publicNotices = getPublicNotices(pnId);
        publicNotices.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        publicNotices.updateAuditFields();
        return publicNoticesDAO.saveOrUpdate(publicNotices);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final PublicNotices publicNotices) {
        publicNotices.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        publicNotices.updateAuditFields();
        return publicNoticesDAO.saveOrUpdate(publicNotices);

    }

    @Override
    @Transactional
    public List<PublicNotices> getPublicNoticesByDeptId(final long deptId, final Organisation organisation) {
        return publicNoticesDAO.getPublicNoticesByDeptId(deptId, organisation);
    }

    @Override
    public List<PublicNoticesHistory> getGuestCitizenPublicNotices(
            final Organisation organisation) {

        return publicNoticesDAO.getGuestPublicNotices(organisation);
    }
    
    @Override
    public boolean checkSequence(final long sequenceNo,Long orgId) {
		return publicNoticesDAO.checkSequence(sequenceNo,orgId);
    }
}

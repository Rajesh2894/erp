/**
 *
 */
package com.abm.mainet.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IEIPAboutUsDAO;
import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Bhavesh.Gadhe
 */
@Service
public class EIPAboutUsService implements IEIPAboutUsService {

    private static final long serialVersionUID = 1522203196709075151L;
    @Autowired
    IEIPAboutUsDAO eipAboutUsDAO;

    @Override
    @Transactional
    public EIPAboutUs getAboutUs(final Organisation organisation, final String isDeleted) {
        return eipAboutUsDAO.getAboutUs(organisation, isDeleted);
    }

    @Override
    @Transactional
    public void saveAboutUs(final EIPAboutUs aboutUs, String chekkerflag) {
        aboutUs.updateAuditFields();
        aboutUs.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        eipAboutUsDAO.saveOrUpdateAboutUs(aboutUs,chekkerflag);
    }

    @Override
    public EIPAboutUsHistory getGuestAboutUs(final Organisation organisation,
            final String isDeleted) {

        return eipAboutUsDAO.getGuestAboutUs(organisation, isDeleted);
    }
}

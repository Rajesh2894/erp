package com.abm.mainet.cms.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IEIPContactUsDAO;
import com.abm.mainet.cms.domain.EIPContactUs;
import com.abm.mainet.cms.domain.EIPContactUsHistory;
import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author Bhavesh.Gadhe
 */
@Service
public class EIPContactUsService implements IEIPContactUsService {

    @Autowired
    private IEIPContactUsDAO eipContactUsDAO;

    private static final Logger LOG = Logger.getLogger(EIPContactUsService.class);

    @Override
    @Transactional
    public void saveContactUs(final EIPContactUs contactUs) {
        contactUs.updateAuditFields();
        contactUs.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        eipContactUsDAO.saveOrUpdateContactUs(contactUs);
    }

    @Override
    @Transactional
    public List<EIPContactUsHistory> getContactUsorg(final Organisation organisation) {
        return eipContactUsDAO.getContactUsorg(organisation);
    }

    @Override
    @Transactional
    public void saveEIPuserContactus(final EipUserContactUs eipUserContactUs) {
        eipUserContactUs.updateAuditFields();
        eipUserContactUs.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        eipContactUsDAO.saveEIPUserContactus(eipUserContactUs);

    }

    @Override
    @Transactional(readOnly = true)
    public List<EIPContactUs> getContactList(final Organisation organisation, String flag) {

        return eipContactUsDAO.getContactList(organisation, flag);
    }

    @Override
    @Transactional(readOnly = true)
    public EIPContactUs editContactInfoDetails(final long rowId, final Organisation organisation) {
        return eipContactUsDAO.editContactInfoDetails(rowId, organisation);
    }

    @Override
    @Transactional
    public boolean delete(final long rowId, final Organisation organisation) {

        try {
            final EIPContactUs eipContactUs = editContactInfoDetails(rowId, organisation);
            eipContactUs.setIsDeleted(MainetConstants.IsDeleted.DELETE);
            eipContactUsDAO.saveOrUpdateContactUs(eipContactUs);
        } catch (final Exception e) {

            LOG.error(MainetConstants.DURING_DELETE_ERROR, e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasSequenceExist(final String flag, final Double sequenceNo, final Organisation organisation) {

        return eipContactUsDAO.hasSequenceExist(flag, sequenceNo, organisation);
    }

    @Override
    @Transactional
    public List<EIPContactUsHistory> getContactUslistBy(final Organisation organisation) {
        return eipContactUsDAO.getContactUsListBy(organisation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EIPContactUsHistory> getAllContactUsListByOrganisation(Organisation organisation) {
    	
    	List<EIPContactUsHistory> conUsList = eipContactUsDAO.getAllContactUsListByOrganisation(organisation);
    	
        return conUsList;
    }
    @Override
    @Transactional
	public List<EIPContactUs> getContactListByOrganisation(Organisation organisation){
    	return eipContactUsDAO.getContactListByOrganisation(organisation);
    }
}

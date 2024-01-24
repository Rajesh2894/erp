/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IOpinionPollDAO;
import com.abm.mainet.cms.dao.IPublicNoticesDAO;
import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import java.util.Date;

/**
 * @author swapnil.shirke
 */
@Service
public class AdminOpinionPollService implements IAdminOpinionPollService, Serializable {

    private static final long serialVersionUID = 2145950281022218233L;

    @Autowired
    IOpinionPollDAO opinionPollDAO;

    @Override
    @Transactional
    public List<OpinionPoll> getAllOpinionPolls(final Organisation organisation, String flag) {
        return opinionPollDAO.getAdminOpinionPolls(organisation, flag);
    }

    @Override
    @Transactional
    public List<OpinionPoll> getCitizenOpinionPolls(final Organisation organisation) {
        return opinionPollDAO.getOpinionPolls(organisation);
    }

    @Override
    @Transactional
    public OpinionPoll getOpinionPoll(final long pnId) {
        return opinionPollDAO.getOpinionPollById(pnId);
    }

    @Override
    @Transactional
    public boolean delete(final long pnId) {
        final OpinionPoll opinionPoll = getOpinionPoll(pnId);
        opinionPoll.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        opinionPoll.updateAuditFields();
        return opinionPollDAO.saveOrUpdate(opinionPoll);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final OpinionPoll opinionPoll) {
    	opinionPoll.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
    	opinionPoll.updateAuditFields();
        return opinionPollDAO.saveOrUpdate(opinionPoll);

    }

    

    @Override
    public List<OpinionPollHistory> getGuestCitizenOpinionPolls(
            final Organisation organisation) {

        return opinionPollDAO.getGuestOpinionPolls(organisation);
    }
    
   
    
    @Override
    public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag) {
    	return opinionPollDAO.getAdminOpinionPollsByValidityAndIssueDate( organisation,flag);
    }
    
    @Override
    public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag,Date validityDate,Date issueDate) {
    	return opinionPollDAO.getAdminOpinionPollsByValidityAndIssueDate( organisation,flag,validityDate, issueDate);
    }
}

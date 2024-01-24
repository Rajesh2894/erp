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
import com.abm.mainet.cms.dao.IOpinionPollOptionDAO;
import com.abm.mainet.cms.dao.IPublicNoticesDAO;
import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.OpinionPollOptionHistory;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 */
@Service
public class AdminOpinionPollOptionService implements IAdminOpinionPollOptionService, Serializable {

    private static final long serialVersionUID = 2145950281022218233L;

    @Autowired
    IOpinionPollOptionDAO opinionPollOptionDAO;

    @Override
    @Transactional
    public List<OpinionPollOption> getAllOpinionPollOptions(final Organisation organisation, String flag) {
        return opinionPollOptionDAO.getAdminOpinionPollOptions(organisation, flag);
    }

    @Override
    @Transactional
    public List<OpinionPollOption> getCitizenOpinionPollOptions(final Organisation organisation) {
        return opinionPollOptionDAO.getOpinionPollOptions(organisation);
    }

    @Override
    @Transactional
    public OpinionPollOption getOpinionPollOption(final long pnId) {
        return opinionPollOptionDAO.getOpinionPollOptionById(pnId);
    }

    @Override
    @Transactional
    public boolean delete(final long pnId) {
        final OpinionPollOption opinionPollOption = getOpinionPollOption(pnId);
        opinionPollOption.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        opinionPollOption.updateAuditFields();
        return opinionPollOptionDAO.saveOrUpdate(opinionPollOption);
    }
    
    
    @Override
    @Transactional
    public boolean deleteAll(final long pnId,final long pOptionId) {
        return opinionPollOptionDAO.deleteAll(pnId,pOptionId);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final OpinionPollOption opinionPollOption) {
    	
    	opinionPollOption.updateAuditFields();
        return opinionPollOptionDAO.saveOrUpdate(opinionPollOption);

    }

    @Override
    @Transactional
    public List<OpinionPollOption> getOpinionPollOptionsByDeptId(final long deptId, final Organisation organisation) {
        return opinionPollOptionDAO.getOpinionPollOptionsByDeptId(deptId, organisation);
    }

    @Override
    public List<OpinionPollOptionHistory> getGuestCitizenOpinionPollOptions(
            final Organisation organisation) {

        return opinionPollOptionDAO.getGuestOpinionPollOptions(organisation);
    }
    
    @Override
    public boolean checkSequence(final long sequenceNo,Long orgId) {
		return opinionPollOptionDAO.checkSequence(sequenceNo,orgId);
    }
    
    @Override
    @Transactional
    public List<OpinionPollOption> getOpinionPollOptionsByOpinionPolltId(final long opinionPollId) {
        return opinionPollOptionDAO.getOpinionPollOptionsByOpinionPollId(opinionPollId);
    }
}

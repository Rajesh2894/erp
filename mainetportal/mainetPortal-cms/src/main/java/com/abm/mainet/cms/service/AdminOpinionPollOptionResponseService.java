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
import com.abm.mainet.cms.dao.IOpinionPollOptionResponseDAO;
import com.abm.mainet.cms.dao.IPublicNoticesDAO;
import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 */
@Service
public class AdminOpinionPollOptionResponseService implements IAdminOpinionPollOptionResponseService, Serializable {

    private static final long serialVersionUID = 2145950281022218233L;

    @Autowired
    IOpinionPollOptionResponseDAO opinionPollOptionResponseDAO;

    @Override
    @Transactional
    public List<OpinionPollOptionResponse> getCitizenOpinionPollOptionResponses(final Organisation organisation) {
        return opinionPollOptionResponseDAO.getOpinionPollOptionResponses(organisation);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final OpinionPollOptionResponse opinionPollOptionResponse) {
    	opinionPollOptionResponse.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
    	opinionPollOptionResponse.updateAuditFields();
    	opinionPollOptionResponse.setPnId(opinionPollOptionResponse.getOpinion().getId());
        return opinionPollOptionResponseDAO.saveOrUpdate(opinionPollOptionResponse);

    }

    @Override
    @Transactional
    public List<OpinionPollOptionResponse> getCitizenOpinionPollOptionResponsesByOpinionPollId(final Organisation organisation,final long opinionPollId) {
        return opinionPollOptionResponseDAO.getCitizenOpinionPollOptionResponsesByOpinionPollId(organisation,opinionPollId);
    }
    
    @Override
    public List<OpinionPollDTO> getOpinionPolls(long pid){
    	return opinionPollOptionResponseDAO.getOpinionPolls(pid);
    }
}

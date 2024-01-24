package com.abm.mainet.mobile.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IFeedBackDAO;
import com.abm.mainet.cms.dao.IOpinionPollOptionResponseDAO;
import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionDTO;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.cms.dto.OptionDTO;
import com.abm.mainet.cms.service.IAdminOpinionPollOptionService;
import com.abm.mainet.cms.service.IAdminOpinionPollService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IEmployeeDAO;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author ranjit.seth
 *
 */

@Service
public class OpinionPollService implements IOpinionPollService {
	
	@Autowired
    IOpinionPollOptionResponseDAO opinionPollOptionResponseDAO;
	
	@Autowired
	IAdminOpinionPollOptionService iAdminOpinionPollOptionService;
	
	@Autowired
	IAdminOpinionPollService iAdminOpinionPollService;
	
	@Autowired
	IOrganisationDAO iOrganisationDAO;
	
	@Autowired
	IEmployeeDAO iEmployeeDAO;
	
	 @Autowired
	 private IFeedBackDAO ifeedBackDAO;
 
	 private static final String employeeName ="NOUSER";
	
	public List<OpinionPollDTO> getOpinionPollResponse(long pnid) {

		try {
			List<OpinionPollDTO> opinionPollDTO = opinionPollOptionResponseDAO.getOpinionPolls(pnid);
			return opinionPollDTO;
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public OpinionDTO getCitizenOpinion(long orgid) {
		Organisation Organisation = iOrganisationDAO.getOrganisationById(orgid, MainetConstants.STATUS.ACTIVE);
    	List<OpinionPoll> list = 
    	iAdminOpinionPollService.getAdminOpinionPollsByValidityAndIssueDate(Organisation, MainetConstants.IsDeleted.NOT_DELETE);
    	OpinionPoll opinionPoll = null;
    	if(list != null && list.size() > 0) {
    		opinionPoll = list.get(0);
    	}else {
    		return null;
    	}
        List<OpinionPollOption> opinionPollOptionList =  iAdminOpinionPollOptionService.getOpinionPollOptionsByOpinionPolltId(opinionPoll.getPnId());
        OpinionDTO opinion=new OpinionDTO();
        opinion.setId(opinionPoll.getPnId());
        opinion.setOpinionEng(opinionPoll.getPollSubEn());
        opinion.setOninionReg(opinionPoll.getPollSubReg());
        opinion.setImgPath(opinionPoll.getImgPath());
        opinion.setDocPath(opinionPoll.getDocPath());
        for (OpinionPollOption opinionPollOption : opinionPollOptionList) {
        	OptionDTO opt = new OptionDTO();
        	opt.setoId(opinionPollOption.getpOptionId());
        	opt.setOptionEn(opinionPollOption.getOptionEn());
        	opt.setOptionRg(opinionPollOption.getOptionReg());
        	opinion.getOptions().add(opt);
		}
        return opinion;
    }
	
	@Transactional
	public boolean saveOpinionPoll(long orgid,OpinionPollOptionResponse opinionPollOptionResponse) {
		try {
			Organisation organisation = iOrganisationDAO.getOrganisationById(orgid, MainetConstants.STATUS.ACTIVE);
			Employee employee = iEmployeeDAO.getEmployeeByLoginName(employeeName, organisation,  MainetConstants.IsDeleted.ZERO);
			opinionPollOptionResponse.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
	    	opinionPollOptionResponse.updateAuditFields();
	    	opinionPollOptionResponse.setOrgId(organisation);
	    	opinionPollOptionResponse.setUpdatedBy(employee);
	    	opinionPollOptionResponse.setUserId(employee);
	    	opinionPollOptionResponse.setLmodDate(new Date());
			opinionPollOptionResponseDAO.saveOrUpdate(opinionPollOptionResponse);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	
	@Transactional
	public boolean saveInnovativeIdea(long orgid, Feedback feedback) {
		try {
			Organisation organisation = iOrganisationDAO.getOrganisationById(orgid, MainetConstants.STATUS.ACTIVE);
			Employee employee = iEmployeeDAO.getEmployeeByLoginName(employeeName, organisation,  MainetConstants.IsDeleted.ZERO);
			feedback.updateAuditFields();
			feedback.setUserId(employee);
			feedback.setOrgId(organisation);
			feedback.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
			ifeedBackDAO.saveOrUpdate(feedback,organisation);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}
	
}

package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.domain.SubLinkMaster;
import com.abm.mainet.cms.dto.OpinionDTO;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.cms.dto.OptionDTO;
import com.abm.mainet.cms.service.IAdminOpinionPollOptionResponseService;
import com.abm.mainet.cms.service.IAdminOpinionPollOptionService;
import com.abm.mainet.cms.service.IAdminOpinionPollService;
import com.abm.mainet.cms.service.ISectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;


/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminOpinionPollOptionResponseFormModel extends AbstractEntryFormModel<OpinionPollOptionResponse> {
    private static final long serialVersionUID = 7405567806236977020L;
    private static final Logger LOG = Logger.getLogger(AdminOpinionPollOptionResponseFormModel.class);

    @Autowired
    private IAdminOpinionPollService iAdminOpinionPollService;

    @Autowired
    private IAdminOpinionPollOptionService iAdminOpinionPollOptionService;
    
    @Autowired
    private IAdminOpinionPollOptionResponseService iAdminOpinionPollOptionResponseService;
    
    @Autowired
    private IEntitlementService iEntitlementService;
    
	@Autowired
    private ISectionService iSectionService;
	  
    
    private OpinionDTO opinion = null;
    
    List<OpinionPollDTO> opinionPollList=null;
    
    @Override
    public void addForm() {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        OpinionPollOptionResponse opinionPollOptionResponse = new OpinionPollOptionResponse();
        
        setEntity(opinionPollOptionResponse);
    }

   

    @Override
    public boolean saveOrUpdateForm() throws FrameworkException{
        final OpinionPollOptionResponse entity = getEntity();
       
        //validateBean(entity, AdminOpinioMasterValidator.class);
        if (hasValidationErrors()) {

            return false;
        }
        iAdminOpinionPollOptionResponseService.saveOrUpdate(entity);
        return true;
    }



	
    
    public OpinionDTO getCitizenOpinion() {
    	List<OpinionPoll> list = 
    	iAdminOpinionPollService.getAdminOpinionPollsByValidityAndIssueDate(UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.NOT_DELETE);
    	OpinionPoll opinionPoll = null;
    	if(list != null && list.size() > 0) {
    		opinionPoll = list.get(0);
    	}else {
    		return null;
    	}
        List<OpinionPollOption> opinionPollOptionList =  iAdminOpinionPollOptionService.getOpinionPollOptionsByOpinionPolltId(opinionPoll.getPnId());
        opinion=new OpinionDTO();
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
    
    public List<OpinionPollDTO> getOpinionPolls(){
    	List<OpinionPoll> list = iAdminOpinionPollService.getAdminOpinionPollsByValidityAndIssueDate(UserSession.getCurrent().getOrganisation(), MainetConstants.IsDeleted.NOT_DELETE);
    	OpinionPoll opinionPoll = null;
    	if(list !=null && list.size() > 0) {
    		opinionPoll = list.get(0);
    	}else {
    		return null;
    	}
    	
    	
    	opinionPollList = iAdminOpinionPollOptionResponseService.getOpinionPolls(opinionPoll.getPnId());
    	return opinionPollList;
    }



	public List<OpinionPollDTO> getOpinionPollList() {
		return opinionPollList;
	}



	public void setOpinionPollList(List<OpinionPollDTO> opinionPollList) {
		this.opinionPollList = opinionPollList;
	}
    
    

}




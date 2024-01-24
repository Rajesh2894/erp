package com.abm.mainet.rts.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.domain.DeathCertificateEntity;

import com.abm.mainet.rts.dto.DeathCertificateDTO;
import com.abm.mainet.rts.repository.DeathCertificateRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class DeathCertificateApprovalServiceImpl implements IDeathCertificateApprovalService{

	@Autowired
	private DeathCertificateRepository deathCertificateRepository;
	
	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private GroupMasterService groupMasterService;
	
	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	
	@Override
	public DeathCertificateDTO getDeathCertificateDetail(@QueryParam("applicationNo") Long applicationNo, @QueryParam("orgId") Long orgId) {
		
		DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
		List<DeathCertificateEntity> deathCertificateEntityList = deathCertificateRepository.FinddeathCertDatabyApplicationNO(applicationNo,orgId);
		BeanUtils.copyProperties(deathCertificateEntityList.get(0), deathCertificateDTO);
		return deathCertificateDTO;
	}

	@Override
	public String updateWorkFlowDeathService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
        try {
        	ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
		return null;
	}

	@Override
	public void updateDeathApproveStatus(DeathCertificateDTO deathCertificateDTO, String status, String lastDecision) {
		TbCfcApplicationMstEntity cfcApplEntiry=new TbCfcApplicationMstEntity();
		ServiceMaster service=new ServiceMaster();
		DeathCertificateEntity TbDeathregEntity=new DeathCertificateEntity();
		cfcApplEntiry.setApmApplicationId(deathCertificateDTO.getApplicationNo());
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(deathCertificateDTO.getApplicationNo(), deathCertificateDTO.getOrgId());
	    BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
		cfcApplEntiry.setApmApplicationDate(new Date());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
		cfcApplEntiry.setTbOrganisation(UserSession.getCurrent().getOrganisation());
		cfcApplEntiry.setUserId(deathCertificateDTO.getUserId());
		service.setSmServiceId(deathCertificateDTO.getSmServiceId());
	    cfcApplEntiry.setUpdatedBy(deathCertificateDTO.getUserId());
	    cfcApplEntiry.setTbServicesMst(service);
		TbDeathregEntity.setDrRId(deathCertificateDTO.getDrRId());
		TbDeathregEntity.setUpdatedBy(deathCertificateDTO.getUserId());
	    TbDeathregEntity.setUpdatedDate(new Date());
	    TbDeathregEntity.setLmoddate(new Date());
	    
	    
	    LookUp lookup = null;
		if (deathCertificateDTO.getDrSex() != null) {
			lookup = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(deathCertificateDTO.getDrSex(), GENDER, deathCertificateDTO.getLangId()); 
		}
		
		if(lastDecision.equals(RtsConstants.REJECTED)){
		    cfcApplEntiry.setApmAppRejFlag(RtsConstants.APMAPPLICATIOREJFLAG);
		    cfcApplEntiry.setAppAppRejBy(deathCertificateDTO.getSmServiceId());
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag(RtsConstants.APMAPPLICATIONCLOSEDFLAG);
		    
		}
		else if(status.equals(RtsConstants.APPROVED) && lastDecision.equals(RtsConstants.PENDING)){
			cfcApplEntiry.setApmApplSuccessFlag(RtsConstants.APMAPPLICATIONPENDINGFLAG);
			cfcApplEntiry.setApmApprovedBy(deathCertificateDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag(RtsConstants.APMAPPLICATIONCLOSEDOPENFLAG);
			
		}
		else if(status.equals(RtsConstants.APPROVED) && lastDecision.equals(RtsConstants.CLOSED)){
			cfcApplEntiry.setApmApplSuccessFlag(RtsConstants.APMAPPLICATIONCLOSEDFLAG);
			cfcApplEntiry.setApmApprovedBy(deathCertificateDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag(RtsConstants.APMAPPLICATIONCLOSEDOPENFLAG);
			
		}
		 tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDeathWorkFlowStatus(Long drRId, String wfStatus, Long orgId, String drstatus) {
		deathCertificateRepository.updateWorkFlowStatus(drRId, orgId, wfStatus,drstatus);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Boolean checkEmployeeRole(UserSession ses) {
		List<LookUp> lookup = CommonMasterUtility.getListLookup(RtsConstants.DESIGN_PRIFIX,
				UserSession.getCurrent().getOrganisation());

		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		for (int i = 0; i < lookup.size(); i++) {
			if (lookup.get(i).getOtherField()!=null && lookup.get(i).getOtherField().equals(RtsConstants.RTS)) {
				if (lookup.get(i).getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
					checkFinalAproval = true;
				}
			}
		}

		return checkFinalAproval;
	}
	
	@Override
	public void smsAndEmailApproval(DeathCertificateDTO deathCertificateDTO ,String decision) {
		CFCApplicationAddressEntity tbCfcApplicationMstAdd=iCFCApplicationMasterDAO.getApplicantsDetailsDao(deathCertificateDTO.getApplicationNo());
				
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
			smdto.setUserId(deathCertificateDTO.getUserId());
			smdto.setAppNo(deathCertificateDTO.getApplicationNo().toString());
			smdto.setMobnumber(tbCfcApplicationMstAdd.getApaMobilno());
			Organisation organisation =UserSession.getCurrent().getOrganisation();
			smdto.setCc(decision);
			smdto.setEmail(tbCfcApplicationMstAdd.getApaEmail());
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					RtsConstants.RTS, "ApplyDeathCertificateLevel.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, deathCertificateDTO.getLangId());

		
	}

}

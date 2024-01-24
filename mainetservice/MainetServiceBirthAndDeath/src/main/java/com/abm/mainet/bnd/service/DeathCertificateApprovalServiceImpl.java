package com.abm.mainet.bnd.service;

import static com.abm.mainet.common.constant.PrefixConstants.MobilePreFix.GENDER;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.domain.DeathCertEntity;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.repository.DeathCertificateRepository;
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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service("DeathCertificateApproval")
public class DeathCertificateApprovalServiceImpl implements IDeathCertificateApprovalService{

	@Autowired
	private DeathCertificateRepository deathCertificateRepositorys;
	
	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private GroupMasterService groupMasterService;
	
	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	
	@Override
	public DeathCertificateDTO getDeathCertificateDetail(@QueryParam("applicationNo") Long applicationNo, @QueryParam("orgId") Long orgId) {
		
		DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
		List<DeathCertEntity> deathCertificateEntityList = deathCertificateRepositorys.FinddeathCertDatabyApplicationNO(applicationNo,orgId);
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
		DeathCertEntity TbDeathregEntity=new DeathCertEntity();
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
		
		if(lastDecision.equals(BndConstants.REJECTED)){
		    cfcApplEntiry.setApmAppRejFlag(BndConstants.APMAPPLICATIOREJFLAG);
		    cfcApplEntiry.setAppAppRejBy(deathCertificateDTO.getSmServiceId());
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag(BndConstants.APMAPPLICATIONCLOSEDFLAG);
		    
		}
		else if(status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.PENDING)){
			cfcApplEntiry.setApmApplSuccessFlag(BndConstants.APMAPPLICATIONPENDINGFLAG);
			cfcApplEntiry.setApmApprovedBy(deathCertificateDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag(BndConstants.APMAPPLICATIONCLOSEDOPENFLAG);
			
		}
		else if(status.equals(BndConstants.APPROVED) && lastDecision.equals(BndConstants.CLOSED)){
			cfcApplEntiry.setApmApplSuccessFlag(BndConstants.APMAPPLICATIONCLOSEDFLAG);
			cfcApplEntiry.setApmApprovedBy(deathCertificateDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag(BndConstants.APMAPPLICATIONCLOSEDOPENFLAG);
			
		}
		 tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDeathWorkFlowStatus(Long drRId, String wfStatus, Long orgId, String drstatus) {
		deathCertificateRepositorys.updateWorkFlowStatus(drRId, orgId, wfStatus,drstatus);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Boolean checkEmployeeRole(UserSession ses) {
		List<LookUp> lookup = CommonMasterUtility.getListLookup(BndConstants.DESIGN_PRIFIX,
				UserSession.getCurrent().getOrganisation());

		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		for (int i = 0; i < lookup.size(); i++) {
			if (lookup.get(i).getOtherField()!=null && lookup.get(i).getOtherField().equals(BndConstants.RTS)) {
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
			smdto.setPlace(deathCertificateDTO.getAuthRemark());
			Organisation organisation =UserSession.getCurrent().getOrganisation();
			smdto.setCc(decision);
			String fullName = String.join(" ", Arrays.asList(deathCertificateDTO.getRequestDTO().getfName(),
					deathCertificateDTO.getRequestDTO().getmName(), deathCertificateDTO.getRequestDTO().getlName()));
			smdto.setAppName(fullName);
			smdto.setEmail(tbCfcApplicationMstAdd.getApaEmail());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			if (MainetConstants.WorkFlow.Decision.APPROVED.equals(decision)) {
				smdto.setRegReason("successfully generated with registration");
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						BndConstants.RTS, BndConstants.DEATHAPPROVAL_URL,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation,
						deathCertificateDTO.getLangId());
			} else if (MainetConstants.WorkFlow.Decision.REJECTED.equals(decision)) {
				smdto.setRegReason("rejected because of these reasons");
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						BndConstants.RTS, BndConstants.DEATHAPPROVAL_URL,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation,
						deathCertificateDTO.getLangId());
			}
		} else {

			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, BndConstants.DEATHAPPROVAL_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG,
					smdto, organisation, deathCertificateDTO.getLangId());

		}
	}			

	@Override
	public void smsAndEmailApprovalForNacDeath(DeathCertificateDTO deathCertificateDTO, String decision) {
		CFCApplicationAddressEntity tbCfcApplicationMstAdd=iCFCApplicationMasterDAO.getApplicantsDetailsDao(deathCertificateDTO.getApplicationNo());
		
		SMSAndEmailDTO smdto = new SMSAndEmailDTO();
			smdto.setUserId(deathCertificateDTO.getUserId());
			smdto.setAppNo(deathCertificateDTO.getApplicationNo().toString());
			smdto.setMobnumber(tbCfcApplicationMstAdd.getApaMobilno());
			Organisation organisation =UserSession.getCurrent().getOrganisation();
			smdto.setCc(decision);
			String fullName = String.join(" ", Arrays.asList(deathCertificateDTO.getRequestDTO().getfName(),
					deathCertificateDTO.getRequestDTO().getmName(), deathCertificateDTO.getRequestDTO().getlName()));
			smdto.setAppName(fullName);
			smdto.setRegNo(deathCertificateDTO.getDrCertNo());
			smdto.setEmail(tbCfcApplicationMstAdd.getApaEmail());
			String alertType = MainetConstants.BLANK;
			if(decision.equals(BndConstants.APPROVED)) {
				alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
			}else {
				alertType = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
			}
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
					BndConstants.RTS, "NacForDeathRegApproval.html", alertType, smdto, organisation, deathCertificateDTO.getLangId());
	}

}

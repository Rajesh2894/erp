package com.abm.mainet.bnd.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeathCauseMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterCorrDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterCorrectionDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.LoiDetail;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;


@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathRegistrationCorrectionApprovalModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	
	private String saveMode;
	
	 @Autowired
	 private IDeathRegistrationService iDeathRegistrationService;
	 
	 @Autowired
	 private IdeathregCorrectionService iDeathregCorrectionService;
	 
	 @Autowired
	 private TbLoiMasService iTbLoiMasService;
	 
	 @Autowired
	 private ServiceMasterService serviceMasterService;
	 
	 @Autowired
	 ISMSAndEmailService ismsAndEmailService;
	 
	 private ServiceMaster serviceMaster = new ServiceMaster();
	 RequestDTO requestDTO = new RequestDTO();
	   private TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
	   
	   private TbBdDeathregCorrDTO tbBdDeathregCorrDTO = new TbBdDeathregCorrDTO();
	   
	   private CemeteryMasterDTO cemeteryMasterDTO = new CemeteryMasterDTO(); 
	
		private List<HospitalMasterDTO> hospitalMasterDTOList;

		private String hospitalList;
	   
		private List<CemeteryMasterDTO> cemeteryMasterDTOList;
		
		private String cemeteryList;
		
		private List<TbDeathregDTO> tbDeathregDTOList;
		
		private String tbDeathRegDtoList;
		
		private MedicalMasterDTO medicalMasterDto = new MedicalMasterDTO();

		private DeceasedMasterDTO deceasedMasterDTO = new DeceasedMasterDTO();
		
		private List<DocumentDetailsVO> checkList = new ArrayList<>();

		private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
		
		private List<TbBdDeathregCorrDTO> tbDeathregcorrDTOList;
		
		private TbBdDeathregCorrDTO tbDeathregcorrDTO = new TbBdDeathregCorrDTO();
		
		private MedicalMasterCorrectionDTO medicalMasterCorrDto = new MedicalMasterCorrectionDTO();
		
		private DeathCauseMasterDTO deathCauseMasterDTO = new DeathCauseMasterDTO();
		
		private DeceasedMasterCorrDTO deceasedMasterCorrDTO = new DeceasedMasterCorrDTO(); 
		private String successFlag;
		private String payableFlag;
		private Double totalLoiAmount;
		private double amountToPay;
		private String printBT;// click from summary Screen ,based on this hide BT
		private List<TbLoiDet> loiDetail = new ArrayList<>();
		private String loiNumber;
		
	
	public String saveDeathRegCorrApprovalDetails(String ApplicationId, Long orgId, String task)
	{
		RequestDTO requestDTO = new RequestDTO();
		String certificateno=null;
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName("BND");
        requestDTO.setServiceId(getServiceId());
        getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));//change vi
        getWorkflowActionDto().setDecision(tbDeathregDTO.getDeathRegstatus());
        tbBdDeathregCorrDTO.setApmApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode("DRC",UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        tbBdDeathregCorrDTO.setSmServiceId(service.getSmServiceId());
        tbBdDeathregCorrDTO.setOrgId(requestDTO.getOrgId());
        tbDeathregDTO.setApmApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
		prepareWorkFlowTaskAction(getWorkflowActionDto());
		getWorkflowActionDto().setReferenceId(null);
		iDeathregCorrectionService.updateWorkFlowDeathService(getWorkflowActionDto());
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
	                .getBean(IWorkflowRequestService.class)
	                .getWorkflowRequestByAppIdOrRefId(tbBdDeathregCorrDTO.getApmApplicationId(),null ,
	                        UserSession.getCurrent().getOrganisation().getOrgid());
		int size = workflowRequest.getWorkFlowTaskList().size();		
		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {     
			iDeathregCorrectionService.updateDeathApproveStatus(tbBdDeathregCorrDTO,null,workflowRequest.getLastDecision());
			iDeathregCorrectionService.updateDeathWorkFlowStatus(tbBdDeathregCorrDTO.getDrId(),workflowRequest.getLastDecision(), orgId);
			iDeathregCorrectionService.updateNoOfIssuedCopy(tbDeathregDTO.getDrId(), tbDeathregDTO.getOrgId(), BndConstants.REJECTED);
			iDeathRegistrationService.smsAndEmailApproval(tbDeathregDTO,workflowRequest.getLastDecision());
	    }
	    if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)
	    		&& workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Decision.PENDING)) {
	    	iDeathregCorrectionService.updateDeathApproveStatus(tbBdDeathregCorrDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	//Current Task Name
	    	String taskName = workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName();
	    	//Previous Task Name
	    	String taskNamePrevious = workflowRequest.getWorkFlowTaskList().get(size-2).getTaskName();
	    	if (!taskName.equals(taskNamePrevious)) {
	    		iDeathregCorrectionService.updateDeathWorkFlowStatus(tbBdDeathregCorrDTO.getDrId(), MainetConstants.WorkFlow.Decision.PENDING, orgId);
	    		tbBdDeathregCorrDTO.setDeathWfStatus(taskNamePrevious);
	    	}	
	    } 
	    if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
	    		&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {	
	    	iDeathregCorrectionService.updateDeathApproveStatus(tbBdDeathregCorrDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	tbBdDeathregCorrDTO.setDeathWfStatus(workflowRequest.getWorkFlowTaskList().get(size-1).getTaskName());  
	    	iDeathregCorrectionService.updateDeathWorkFlowStatus(tbBdDeathregCorrDTO.getDrId(),workflowRequest.getLastDecision(), orgId);
	    	//certificate generation/update
	    	BeanUtils.copyProperties(tbDeathregDTO, tbBdDeathregCorrDTO);
	    	certificateno=iDeathRegistrationService.updateDeathRegCorrApprove(tbBdDeathregCorrDTO,workflowRequest.getLastDecision(),workflowRequest.getStatus());
	    	
	    	// save data to death registration entity after final approval 
	    	tbDeathregDTO.setDrId(tbDeathregDTO.getDrId());
	    	tbDeathregDTO.setApmApplicationId(tbBdDeathregCorrDTO.getApmApplicationId());
	    	TbDeathregDTO saveDeathDet = iDeathRegistrationService.saveDeathRegDetOnApproval(tbDeathregDTO);
	    	iDeathRegistrationService.updatNoOfcopyStatus(tbDeathregDTO.getDrId(), orgId, tbDeathregDTO.getDrId(), saveDeathDet.getNumberOfCopies());
	    	tbDeathregDTO.setDrCertNo(certificateno);
	    	iDeathRegistrationService.smsAndEmailApproval(tbDeathregDTO,workflowRequest.getLastDecision());
	     }
	    
		return certificateno;
	}
	
	
	 private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
	        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
	        workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
	        workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
	        workflowActionDto.setDateOfAction(new Date());
	        workflowActionDto.setCreatedDate(new Date());
	        workflowActionDto.setComments(tbDeathregDTO.getAuthRemark());
	        workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	        workflowActionDto.setReferenceId(getWorkflowActionDto().getReferenceId());
	        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
	        workflowActionDto.setIsFinalApproval(false);
	        return workflowActionDto;
	    }

	public boolean saveLoiData() {
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DRC,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(serviceMas);
		this.setServiceName(serviceMas.getSmServiceName());
		boolean status = false;
		int noOfCopies = 0;//need to add from dto
		Map<Long, Double> loiCharges = iDeathRegistrationService.getLoiCharges(
				UserSession.getCurrent().getOrganisation().getOrgid(), noOfCopies, requestDTO.getApplicationId(),serviceMas);;
		if (MapUtils.isNotEmpty(loiCharges)) {
			saveLOIAppData(loiCharges, serviceMas.getSmServiceId(), loiDetail, true,
					getWorkflowActionDto());
			setLoiDetail(loiDetail);
			status = true;
		}
		return status;

	}

	public TbLoiMas saveLOIAppData(Map<Long, Double> loiCharges, Long serviceId, List<TbLoiDet> loiDetList,
			Boolean approvalLetterGenerationApplicable, WorkflowTaskAction wfActionDto) {
		TbLoiMas loiMasDto = new TbLoiMas();
		final UserSession session = UserSession.getCurrent();
		final Long paymentType = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagC,
				PrefixConstants.LookUpPrefix.LPT, session.getOrganisation()).getLookUpId();

		loiMasDto.setOrgid(session.getOrganisation().getOrgid());
		loiMasDto.setUserId(session.getEmployee().getEmpId());
		loiMasDto.setLgIpMac(session.getEmployee().getLgIpMac());
		loiMasDto.setLmoddate(new Date());
		loiMasDto.setLoiPaid(MainetConstants.Common_Constant.NO);
		loiMasDto.setLoiStatus(MainetConstants.FlagA);
		loiMasDto.setLoiDate(new Date());
		loiMasDto.setLoiYear(Calendar.getInstance().get(Calendar.YEAR));
		loiMasDto.setLoiServiceId(serviceId);
		loiMasDto.setLoiRefId(requestDTO.getApplicationId());
		loiMasDto.setLoiApplicationId(requestDTO.getApplicationId());

		Long taxId = null;
		Double taxAmount = new Double(0);
		Double totalAmount = new Double(0);
		for (final Entry<Long, Double> loiCharge : loiCharges.entrySet()) {
			TbLoiDet loiDetails = new TbLoiDet();
			taxId = loiCharge.getKey();
			taxAmount = loiCharge.getValue();
			loiDetails.setLoiAmount(BigDecimal.valueOf(taxAmount));
			loiDetails.setOrgid(session.getOrganisation().getOrgid());
			loiDetails.setLoiChrgid(taxId);
			loiDetails.setLgIpMac(session.getEmployee().getLgIpMac());
			loiDetails.setUserId(session.getEmployee().getEmpId());
			loiDetails.setLoiCharge(MainetConstants.Common_Constant.YES);
			loiDetails.setLoiPaytype(paymentType);
			loiDetails.setLmoddate(new Date());
			loiDetList.add(loiDetails);
			totalAmount = totalAmount + taxAmount;
		}

		loiMasDto.setLoiAmount(BigDecimal.valueOf(totalAmount));
		iTbLoiMasService.saveLoiDetails(loiMasDto, loiDetList, null);
		LoiDetail loiDetail = new LoiDetail();
		loiDetail.setLoiNumber(loiMasDto.getLoiNo());
		setLoiNumber(loiMasDto.getLoiNo());
		if (totalAmount != 0) {
			loiDetail.setLoiPaymentApplicable(true);
		} else {
			loiDetail.setLoiPaymentApplicable(false);
		}

		loiDetail.setIsComplianceApplicable(false);
		loiDetail.setIsApprovalLetterGenerationApplicable(approvalLetterGenerationApplicable);
		wfActionDto.setLoiDetails(new ArrayList<>());
		wfActionDto.getLoiDetails().add(loiDetail);
		return loiMasDto;
	}
	
	public void sendSmsAndEmailLoi(TbDeathregDTO entity) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		String fullName = String.join(" ", Arrays.asList(entity.getRequestDTO().getfName(),
				entity.getRequestDTO().getmName(), entity.getRequestDTO().getlName()));
		dto.setAppName(fullName);
		dto.setAppNo(String.valueOf(entity.getApmApplicationId()));
		dto.setLoiAmt(String.valueOf(entity.getAmount()));
		dto.setEmail(entity.getRequestDTO().getEmail());
		dto.setMobnumber(entity.getRequestDTO().getMobileNo());
		dto.setLoiNo(getLoiNumber());
		String serviceName = serviceMasterService.getServiceNameByServiceId(entity.getServiceId());
		if (StringUtils.isNotBlank(serviceName))
			dto.setServName(serviceName);
		dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		// to sent smsAndEmail in both language English and regional
		int langId = UserSession.getCurrent().getLanguageId();
		dto.setUserId(entity.getUserId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				MainetConstants.SMS_EMAIL_URL.LOI_GENERATION, PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto,
				UserSession.getCurrent().getOrganisation(), langId);
}
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public IDeathRegistrationService getiDeathRegistrationService() {
		return iDeathRegistrationService;
	}
	public void setiDeathRegistrationService(IDeathRegistrationService iDeathRegistrationService) {
		this.iDeathRegistrationService = iDeathRegistrationService;
	}

	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}
	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}
	public String getHospitalList() {
		return hospitalList;
	}
	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}
	public List<CemeteryMasterDTO> getCemeteryMasterDTOList() {
		return cemeteryMasterDTOList;
	}
	public List<CemeteryMasterDTO> setCemeteryMasterDTOList(List<CemeteryMasterDTO> cemeteryMasterDTOList) {
		return this.cemeteryMasterDTOList = cemeteryMasterDTOList;
	}
	public String getCemeteryList() {
		return cemeteryList;
	}
	public void setCemeteryList(String cemeteryList) {
		this.cemeteryList = cemeteryList;
	}
	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}
	public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		this.tbDeathregDTOList = tbDeathregDTOList;
	}
	public String getTbDeathRegDtoList() {
		return tbDeathRegDtoList;
	}
	public void setTbDeathRegDtoList(String tbDeathRegDtoList) {
		this.tbDeathRegDtoList = tbDeathRegDtoList;
	}
	public MedicalMasterDTO getMedicalMasterDto() {
		return medicalMasterDto;
	}
	public void setMedicalMasterDto(MedicalMasterDTO medicalMasterDto) {
		this.medicalMasterDto = medicalMasterDto;
	}
	public DeceasedMasterDTO getDeceasedMasterDTO() {
		return deceasedMasterDTO;
	}
	public void setDeceasedMasterDTO(DeceasedMasterDTO deceasedMasterDTO) {
		this.deceasedMasterDTO = deceasedMasterDTO;
	}
	
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public TbBdDeathregCorrDTO getTbDeathregcorrDTO() {
		return tbDeathregcorrDTO;
	}
	public void setTbDeathregcorrDTO(TbBdDeathregCorrDTO tbDeathregcorrDTO) {
		this.tbDeathregcorrDTO = tbDeathregcorrDTO;
	}
	
	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}
	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public List<TbBdDeathregCorrDTO> getTbDeathregcorrDTOList() {
		return tbDeathregcorrDTOList;
	}

	public void setTbDeathregcorrDTOList(List<TbBdDeathregCorrDTO> tbDeathregcorrDTOList) {
		this.tbDeathregcorrDTOList = tbDeathregcorrDTOList;
	}

    public CemeteryMasterDTO getCemeteryMasterDTO() {
    	return cemeteryMasterDTO;
    }

	public void setCemeteryMasterDTO(CemeteryMasterDTO cemeteryMasterDTO) {
		this.cemeteryMasterDTO = cemeteryMasterDTO;
	}

	public MedicalMasterCorrectionDTO getMedicalMasterCorrDto() {
		return medicalMasterCorrDto;
	}

	public void setMedicalMasterCorrDto(MedicalMasterCorrectionDTO medicalMasterCorrDto) {
		this.medicalMasterCorrDto = medicalMasterCorrDto;
	}
	
	public DeathCauseMasterDTO getDeathCauseMasterDTO() {
		return deathCauseMasterDTO;
	}

	public void setDeathCauseMasterDTO(DeathCauseMasterDTO deathCauseMasterDTO) {
		this.deathCauseMasterDTO = deathCauseMasterDTO;
	}

	public DeceasedMasterCorrDTO getDeceasedMasterCorrDTO() {
		return deceasedMasterCorrDTO;
	}

	public void setDeceasedMasterCorrDTO(DeceasedMasterCorrDTO deceasedMasterCorrDTO) {
		this.deceasedMasterCorrDTO = deceasedMasterCorrDTO;
	}


	public String getSuccessFlag() {
		return successFlag;
	}


	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}


	public String getPayableFlag() {
		return payableFlag;
	}


	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}


	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}


	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}


	public double getAmountToPay() {
		return amountToPay;
	}


	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}


	public String getPrintBT() {
		return printBT;
	}


	public void setPrintBT(String printBT) {
		this.printBT = printBT;
	}


	public List<TbLoiDet> getLoiDetail() {
		return loiDetail;
	}


	public void setLoiDetail(List<TbLoiDet> loiDetail) {
		this.loiDetail = loiDetail;
	}


	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}


	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}


	public RequestDTO getRequestDTO() {
		return requestDTO;
	}


	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}


	public String getLoiNumber() {
		return loiNumber;
	}


	public void setLoiNumber(String loiNumber) {
		this.loiNumber = loiNumber;
	}
	
	
	
	
}

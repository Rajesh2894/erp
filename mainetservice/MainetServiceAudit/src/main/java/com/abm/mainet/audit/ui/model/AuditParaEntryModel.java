package com.abm.mainet.audit.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.audit.constant.IAuditConstants;
import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.service.IAuditParaEntryService;
import com.abm.mainet.audit.ui.validator.AuditParaEntryValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;



@Component
@Scope("session")
public class AuditParaEntryModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3263148945386283369L;
	
	private static final Logger LOGGER = Logger.getLogger(AuditParaEntryModel.class);

	private AuditParaEntryDto auditParaEntryDto = new AuditParaEntryDto();

	List<AuditParaEntryDto> auditParaEntryDtoList = new ArrayList<>();

	private String saveMode;
	
	private String removeFileById;
	
	private String resolutionComments;

	private List<TbLocationMas> locMasList = new ArrayList<>();

	@Autowired
	private IAuditParaEntryService auditParaEntryService;
	
	@Autowired
    private IFileUploadService fileUploadservice;
	
	@Autowired
    private IEmployeeService iEmployeeService;
	
	@Resource
	private DepartmentService departmentService;
	
	@Autowired
    private TbAcVendormasterService tbAcVendormasterService;
	
	@Resource
	private TbFinancialyearService tbFinancialyearService;
	
	@Autowired
    public IFileUploadService fileUpload;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<AttachDocs> attachDocumentList = new ArrayList<>();

	public AuditParaEntryDto getAuditParaEntryDto() {
		return auditParaEntryDto;
	}

	public void setAuditParaEntryDto(AuditParaEntryDto auditParaEntryDto) {
		this.auditParaEntryDto = auditParaEntryDto;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<AuditParaEntryDto> getAuditParaEntryDtoList() {

		return auditParaEntryDtoList;
	}

	public void setAuditParaEntryDtoList(List<AuditParaEntryDto> auditParaEntryDtoList) {
		this.auditParaEntryDtoList = auditParaEntryDtoList;
	}

	public List<TbLocationMas> getLocMasList() {
		return locMasList;
	}

	public void setLocMasList(List<TbLocationMas> locMasList) {
		this.locMasList = locMasList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	
	public List<AttachDocs> getAttachDocumentList() {
		return attachDocumentList;
	}

	public void setAttachDocumentList(List<AttachDocs> attachDocumentList) {
		this.attachDocumentList = attachDocumentList;
	}
	
	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	@Override
	public boolean saveForm() {
		validateBean(auditParaEntryDto, AuditParaEntryValidator.class);
		if (this.hasValidationErrors()) {
			return false;
		}
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(auditParaEntryDto.getMode(), IAuditConstants.AUDIT_PARA_STATUS_PREFIX, org);
		
		auditParaEntryDto.setAuditParaStatus(lookup.getLookUpId());
		auditParaEntryDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		String finYear = "";
		String auditParaCode="";
		Long fromYear = auditParaEntryDto.getAuditParaYear();
		try {
			finYear = Utility.getFinancialYearFromDate(auditParaEntryDto.getAuditEntryDate());
		} catch (Exception e) {
			LOGGER.error(IAuditConstants.AUDIT_PARA_FINANCIAL_YEAR_CHECK, e);
			throw new FrameworkException(IAuditConstants.AUDIT_PARA_FINANCIAL_YEAR_CHECK);
		}
		if (auditParaEntryDto.getMode().equalsIgnoreCase(IAuditConstants.AUDIT_PARA_STATUS_SUBMIT)) {
			List<LookUp> lookUpList = null;
			try {
				lookUpList = CommonMasterUtility.lookUpListByPrefix(IAuditConstants.AUDIT_PARA_SEQUENCE_PREFIX, UserSession.getCurrent().getOrganisation().getOrgid());
				
			}catch (Exception e) {
					
			}
				if(lookUpList != null && auditParaEntryDto.getAuditParaCode() == null) {
					
					String deptCode =  departmentService.getDeptCode(auditParaEntryDto.getAuditDeptId());
					
					LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(deptCode, IAuditConstants.AUDIT_PARA_SEQUENCE_PREFIX, org);
					String lookupOtherField = lookUp.getOtherField();
					
					String finyear = tbFinancialyearService.findFinancialYearDesc(fromYear);
					
					Long resetId = 0L;
					
					try {
						String s1=Long.toString(auditParaEntryDto.getAuditDeptId());    // converting long to String
				        String s2=Long.toString(fromYear);
				        String s3=s1+s2;
				        resetId =Long.valueOf(s3).longValue();
					} catch(Exception e) {
						resetId = fromYear;
					}
					
					final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
							.generateSequenceNo(IAuditConstants.AUDIT_DEPT_CODE,
									IAuditConstants.AUDIT_TABLE, IAuditConstants.AUDIT_SEQ_COLUMN_NAME, org.getOrgid(),
									MainetConstants.FlagF, resetId);
					
					
					String auditParaCodeWithDept = lookupOtherField +  MainetConstants.operator.DOT + sequence.toString() + MainetConstants.WINDOWS_SLASH +  finyear;
					
						auditParaEntryDto.setAuditParaCode(auditParaCodeWithDept);
				}
				else if (auditParaEntryDto.getAuditParaCode() == null) {
				auditParaCode=createAuditParaCode(finYear);
				auditParaEntryDto.setAuditParaCode(auditParaCode);
				//auditParaEntryDto.setAuditParaYear(Long.parseLong(finYear.substring(0, 4)));
			}
		}
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setReferenceId(auditParaEntryDto.getAuditParaCode());
			long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			requestDTO.setOrgId(orgid);
			requestDTO.setDepartmentName("AD");
			
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(IAuditConstants.AUDIT_PARA_APPROVAL_SERVICE_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
			requestDTO.setServiceId(service.getSmServiceId());
			List<DocumentDetailsVO> docs = new ArrayList<>();
			DocumentDetailsVO document = new DocumentDetailsVO();
			document.setDocumentSerialNo(1L);
			docs.add(document);
			setAttachments(fileUpload.prepareFileUpload(docs));
	        fileUpload.doFileUpload(getAttachments(), requestDTO);
			List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext().getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(auditParaEntryDto.getAuditParaCode(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			auditParaEntryDto.setAttacheMentIds(attacheMentIds);

		/* Work Flow needs to be Initiated for Open Status */
		if (auditParaEntryDto.getMode().equalsIgnoreCase(IAuditConstants.AUDIT_PARA_STATUS_SUBMIT)) 
		{
			Long workflowSuccess = 0L;

			if (auditParaEntryDto.getCreatedBy() == null) {
				auditParaEntryDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				auditParaEntryDto.setCreatedDate(new Date());
				auditParaEntryDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				//auditParaEntryDto.setAuditWard1(UserSession.getCurrent().getLoggedLocId());
				if (StringUtils.isNotEmpty(resolutionComments))
		            this.getAuditParaEntryDto().setAuditAppendix(resolutionComments);
				
			} else {
				auditParaEntryDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				auditParaEntryDto.setUpdatedDate(new Date());
				auditParaEntryDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				if (StringUtils.isNotEmpty(resolutionComments))
		            this.getAuditParaEntryDto().setAuditAppendix(resolutionComments);
				
				
			}
			auditParaEntryService.saveAuditParaEntryService(auditParaEntryDto,saveMode);
			/* Workflow code will be success if it returns value other than 0L or null */
			
			workflowSuccess = auditParaEntryService.initiateWorkFlow(auditParaEntryDto);
						
			if (workflowSuccess == 0L )
			{
				// workflow failed
				setSuccessMessage(ApplicationSession.getInstance().getMessage("audit.mgmt.auditPara.submit.workflow.issue"));
				lookup = CommonMasterUtility.getValueFromPrefixLookUp(IAuditConstants.AUDIT_PARA_STATUS_SAVE, IAuditConstants.AUDIT_PARA_STATUS_PREFIX, org);
			    auditParaEntryService.updateAuditParaStatus(auditParaCode, lookup.getLookUpId());
				
			}
			else
			{
				//workflow success
				// add a logic to pass audit Para Id
				auditParaEntryDto.setAuditParaChk(workflowSuccess);
				auditParaEntryService.updateHistoryforWorkflow(auditParaEntryDto, MainetConstants.MODE_EDIT);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("audit.mgmt.auditPara.submit.success",
						new Object[] { auditParaEntryDto.getAuditParaCode() }));
			}
			//SMS Mail integration
			smsAndEmail( auditParaEntryDto, org);
		}
		else if (auditParaEntryDto.getMode().equalsIgnoreCase(IAuditConstants.AUDIT_PARA_STATUS_SAVE)) 
		{
			if (auditParaEntryDto.getCreatedBy() == null) {
				auditParaEntryDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				auditParaEntryDto.setCreatedDate(new Date());
				auditParaEntryDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				if (StringUtils.isNotEmpty(resolutionComments))
		            this.getAuditParaEntryDto().setAuditAppendix(resolutionComments);
				//auditParaEntryDto.setAuditWard1(UserSession.getCurrent().getLoggedLocId());
				auditParaEntryService.saveAuditParaEntryService(auditParaEntryDto,saveMode);
						setSuccessMessage(ApplicationSession.getInstance().getMessage(ApplicationSession.getInstance().getMessage("audit.para.msg.save.mode")));


			} else {
				auditParaEntryDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				auditParaEntryDto.setUpdatedDate(new Date());
				
				auditParaEntryDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				if (StringUtils.isNotEmpty(resolutionComments))
		            this.getAuditParaEntryDto().setAuditAppendix(resolutionComments);
				auditParaEntryService.saveAuditParaEntryService(auditParaEntryDto,saveMode);
				setSuccessMessage(ApplicationSession.getInstance().getMessage(ApplicationSession.getInstance().getMessage("audit.para.msg.save.mode")));
			}
		}
		auditParaEntryService.updateAuditWfStatus(auditParaEntryDto.getAuditParaCode(), "Start");
		prepareDocumentsData(auditParaEntryDto);
		removeUploadedFiles(this.getRemoveFileById());		
		return true;

	}
	
	private void removeUploadedFiles(String removeFileByIds) {		
		List<Long> removeFileById = null;
		String fileId =this.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {				
			tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, UserSession.getCurrent().getEmployee().getEmpId());
		}
	}

	private void smsAndEmail(AuditParaEntryDto auditParaEntryDto,Organisation organisation)
	{
		List<Object[]> workFlowTask= auditParaEntryService.findWorkFlowTaskByRefId(auditParaEntryDto.getAuditParaCode(), auditParaEntryDto.getOrgId());		
		if(workFlowTask!=null) {
		for(Object[] file:workFlowTask) {
			Object obj=file[18];	
			String[] ids=obj.toString().split(MainetConstants.operator.COMMA);
			for(String id:ids) {	
				Employee emp=iEmployeeService.findEmployeeByIdAndOrgId(Long.parseLong(id), auditParaEntryDto.getOrgId());			   
				SMSAndEmailDTO smdto = new SMSAndEmailDTO();
				smdto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				smdto.setAppNo(auditParaEntryDto.getAuditParaCode());	
				smdto.setMobnumber(emp.getEmpmobno());
				smdto.setEmail(emp.getEmpemail());
				ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
						IAuditConstants.AUDIT_DEPT_CODE, IAuditConstants.AUDIT_PARA_ENTRY_URL, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smdto, organisation, UserSession.getCurrent().getLanguageId());

			}
			
		}
		}
		
	}

	public String createAuditParaCode(String finacialYear) {
		finacialYear = finacialYear.substring(0,4) + MainetConstants.HYPHEN + finacialYear.substring(7);
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		String code = UserSession.getCurrent().getOrganisation().getOrgShortNm() + MainetConstants.WINDOWS_SLASH
				+ finacialYear + MainetConstants.WINDOWS_SLASH;
		LookUp lkp = CommonMasterUtility.lookUpByLookUpIdAndPrefix(auditParaEntryDto.getAuditType(), IAuditConstants.AUDIT_PARA_AUDIT_TYPE_PREFIX, orgid);

		String auditType = lkp.getLookUpCode();
		code = code + auditType + MainetConstants.WINDOWS_SLASH;

		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo(IAuditConstants.AUDIT_DEPT_CODE, IAuditConstants.AUDIT_TABLE, IAuditConstants.AUDIT_SEQ_COLUMN_NAME, orgid, MainetConstants.FlagC,
						orgid);
		code = code + sequence.toString();

		return code;
		

	}
	
	
	public void prepareDocumentsData(AuditParaEntryDto entity) {
		 FileUploadDTO uploadDTO = new FileUploadDTO();
	        uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        uploadDTO.setStatus(MainetConstants.FlagA);
	        uploadDTO.setDepartmentName("AD");
	        uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        uploadDTO.setIdfId(IAuditConstants.AUDIT_DEPT_CODE + MainetConstants.WINDOWS_SLASH + entity.getAuditParaId());
	        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
	                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
	        fileUploadservice.doMasterFileUpload(getAttachments(), uploadDTO);
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
	
	
	

}

/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;
import com.abm.mainet.socialsecurity.repository.SchemeApplicationFormRepository;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.ViewDtoList;
import com.abm.mainet.socialsecurity.ui.validator.ApplicationFormValidator;

/**
 * @author priti.singh
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class SchemeApplicationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 1387449222271618470L;
	private ApplicationFormDto applicationformdto = new ApplicationFormDto();
	private List<Object[]> serviceList = new ArrayList<>();
	private List<LookUp> genderList = new ArrayList<>();
	private List<LookUp> educationList = new ArrayList<>();
	private List<LookUp> maritalstatusList = new ArrayList<>();
	private List<LookUp> categoryList = new ArrayList<>();
	private List<LookUp> typeofdisabilityList = new ArrayList<>();
	private List<BankMasterEntity> bankList = new ArrayList<>();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<ApplicationFormDto> applicantDtoList=new ArrayList<ApplicationFormDto>();
	private List<LookUp> subTypeList = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private Long parentOrgId;
	private String checkListApplFlag;

	private List<LookUp> bplList = new ArrayList<>();

	private List<ViewDtoList> viewList = new ArrayList<>();

	private ServiceMaster serviceMaster = new ServiceMaster();
	private String isFamilyDetReq;
	private String isLifeCertificateReq;
	private String annualIncomeCheck;
	private String saveMode;
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	private String applicationchargeApplFlag;
	private Double charges = 0.0d;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private List<String> banklt;
	

	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;
	@Autowired
	private ServiceMasterService serviceMasterService;
	@Autowired
	private TbDepartmentService tbDepartmentService;
	@Autowired
	private BankMasterService bankMasterService;
	@Autowired
	private IFileUploadService fileUpload;
	@Autowired
	private ISMSAndEmailService ismsAndEmailService;
	
	@Autowired
	private IChallanService iChallanService;

	/**
	 * @return the applicationformdto
	 */
	public ApplicationFormDto getApplicationformdto() {
		return applicationformdto;
	}

	/**
	 * @param applicationformdto the applicationformdto to set
	 */
	public void setApplicationformdto(ApplicationFormDto applicationformdto) {
		this.applicationformdto = applicationformdto;
	}

	/**
	 * @return the serviceList
	 */
	public List<Object[]> getServiceList() {
		return serviceList;
	}

	/**
	 * @param serviceList the serviceList to set
	 */
	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * @return the educationList
	 */
	public List<LookUp> getEducationList() {
		return educationList;
	}

	/**
	 * @param educationList the educationList to set
	 */
	public void setEducationList(List<LookUp> educationList) {
		this.educationList = educationList;
	}

	/**
	 * @return the maritalstatusList
	 */
	public List<LookUp> getMaritalstatusList() {
		return maritalstatusList;
	}

	/**
	 * @param maritalstatusList the maritalstatusList to set
	 */
	public void setMaritalstatusList(List<LookUp> maritalstatusList) {
		this.maritalstatusList = maritalstatusList;
	}

	/**
	 * @return the categoryList
	 */
	public List<LookUp> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<LookUp> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the typeofdisabilityList
	 */
	public List<LookUp> getTypeofdisabilityList() {
		return typeofdisabilityList;
	}

	/**
	 * @param typeofdisabilityList the typeofdisabilityList to set
	 */
	public void setTypeofdisabilityList(List<LookUp> typeofdisabilityList) {
		this.typeofdisabilityList = typeofdisabilityList;
	}

	/**
	 * @return the bplList
	 */
	public List<LookUp> getBplList() {
		return bplList;
	}

	/**
	 * @param bplList the bplList to set
	 */
	public void setBplList(List<LookUp> bplList) {
		this.bplList = bplList;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the genderList
	 */
	public List<LookUp> getGenderList() {
		return genderList;
	}

	/**
	 * @param genderList the genderList to set
	 */
	public void setGenderList(List<LookUp> genderList) {
		this.genderList = genderList;
	}

	/**
	 * @return the bankList
	 */
	public List<BankMasterEntity> getBankList() {
		return bankList;
	}

	/**
	 * @param bankList the bankList to set
	 */
	public void setBankList(List<BankMasterEntity> bankList) {
		this.bankList = bankList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	/**
	 * @return the viewList
	 */
	public List<ViewDtoList> getViewList() {
		return viewList;
	}

	/**
	 * @param viewList the viewList to set
	 */
	public void setViewList(List<ViewDtoList> viewList) {
		this.viewList = viewList;
	}

	/**
	 * @return the schemeApplicationFormService
	 */
	public ISchemeApplicationFormService getSchemeApplicationFormService() {
		return schemeApplicationFormService;
	}

	/**
	 * @param schemeApplicationFormService the schemeApplicationFormService to set
	 */
	public void setSchemeApplicationFormService(ISchemeApplicationFormService schemeApplicationFormService) {
		this.schemeApplicationFormService = schemeApplicationFormService;
	}

	/**
	 * @return the serviceMasterService
	 */
	public ServiceMasterService getServiceMasterService() {
		return serviceMasterService;
	}

	/**
	 * @param serviceMasterService the serviceMasterService to set
	 */
	public void setServiceMasterService(ServiceMasterService serviceMasterService) {
		this.serviceMasterService = serviceMasterService;
	}

	/**
	 * @return the tbDepartmentService
	 */
	public TbDepartmentService getTbDepartmentService() {
		return tbDepartmentService;
	}

	/**
	 * @param tbDepartmentService the tbDepartmentService to set
	 */
	public void setTbDepartmentService(TbDepartmentService tbDepartmentService) {
		this.tbDepartmentService = tbDepartmentService;
	}

	/**
	 * @return the bankMasterService
	 */
	public BankMasterService getBankMasterService() {
		return bankMasterService;
	}

	/**
	 * @param bankMasterService the bankMasterService to set
	 */
	public void setBankMasterService(BankMasterService bankMasterService) {
		this.bankMasterService = bankMasterService;
	}

	/**
	 * @return the fileUpload
	 */
	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return the serviceMaster
	 */
	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	/**
	 * @param serviceMaster the serviceMaster to set
	 */
	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	/**
	 * @return the parentOrgId
	 */
	public Long getParentOrgId() {
		return parentOrgId;
	}

	/**
	 * @param parentOrgId the parentOrgId to set
	 */
	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	@Override
	public boolean saveForm() {
		ApplicationFormDto statusDto = new ApplicationFormDto();
		try {
			statusDto = schemeApplicationFormService.saveApplicationDetails(getApplicationformdto());

			setSuccessMessage(getAppSession().getMessage("social.success.msg") + statusDto.getMasterAppId());

			if(StringUtils.isEmpty(getApplicationformdto().getStatus())||(getApplicationformdto().getStatus()!=null && !getApplicationformdto().getStatus().equals("D"))) {
           // if(!getApplicationformdto().getStatus().equals("D")) {
            	
             if(this.getApplicationchargeApplFlag().equals(MainetConstants.FlagY)) {
            		CommonChallanDTO offline = getOfflineDTO();
    				setOfflineDetailsDTO(offline, this);
    			}
            // sms and email on application form submit
			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			Organisation org = UserSession.getCurrent().getOrganisation();
			ServiceMaster serMast = serviceMasterService.getServiceMaster(getApplicationformdto().getSelectSchemeName(), org.getOrgid());
			smsDto.setMobnumber(getApplicationformdto().getMobNum().toString());
			smsDto.setEmail(getApplicationformdto().getEmail());
			smsDto.setAppNo(getApplicationformdto().getMasterAppId().toString());
			if(serMast!=null)
			smsDto.setSubtype(serMast.getSmServiceName());
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(MainetConstants.SocialSecurity.SERVICE_CODE,
							UserSession.getCurrent().getOrganisation().getOrgid());
			setServiceMaster(sm);
			smsDto.setServName(sm.getSmServiceName());
			String url = "SchemeApplicationForm.html";
			org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			int langId = UserSession.getCurrent().getLanguageId();
			ismsAndEmailService.sendEmailSMS("SWD", url, PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smsDto, org,
					langId);
            }

		} catch (FrameworkException exp) {
			statusDto.setStatusFlag(false);
			throw new FrameworkException(exp);
		}

		return statusDto.getStatusFlag();
	}

	public void getAndSetPrefix(Long orgId, int langId, Organisation org) {
		List<LookUp> parentPxList = CommonMasterUtility.getNextLevelData(MainetConstants.SocialSecurity.FTR, 1, orgId);
		setGenderList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("GNR"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setEducationList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("EDU"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setMaritalstatusList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("MLS"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setCategoryList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("CTY"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		setTypeofdisabilityList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("DSY"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		parentPxList.stream().forEach(s -> { if
			 (s.getLookUpCode().equalsIgnoreCase("BPL")) {
		setBplList(schemeApplicationFormService.FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
				parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("BPL"))
						.collect(Collectors.toList()).get(0).getLookUpId(),
				2L));
		}
		});
		
		/*
		 * parentPxList.stream().forEach(s -> { if
		 * (s.getLookUpCode().equalsIgnoreCase("SS")) {
		 * setSubTypeList(schemeApplicationFormService.
		 * FindSecondLevelPrefixByFirstLevelPxCode(orgId, "FTR",
		 * parentPxList.stream().filter(k -> k.getLookUpCode().equalsIgnoreCase("SS"))
		 * .collect(Collectors.toList()).get(0).getLookUpId(), 2L)); } });
		 */
		
		Long depId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldStatus.getLookUpId();

		List<SocialSecuritySchemeMaster> schemeMaster = schemeApplicationFormService.getActiveScheme(orgId);
		// setServiceList(serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId,
		// depId, activeStatusId, "N"));
		List<Object[]> schemeMastServiceList = serviceMasterService.findAllActiveServicesWhichIsNotActual(orgId, depId,
				activeStatusId, "N");
		List<Object[]> schemeservicelist1 = new ArrayList();
		schemeMaster.stream().forEach(k -> {
			schemeMastServiceList.stream().forEach(l -> {
				if (k.getSchemeNameId().equals(l[0])) {
					schemeservicelist1.add(l);
				}
			});
		});
		setServiceList(schemeservicelist1);
		if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL ))){
		setBanklt(bankMasterService.getBankDetails());
		}
		else {
		setBankList(bankMasterService.getBankList());
		}
	}

	public boolean callWorkFlow() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
		Employee emp = UserSession.getCurrent().getEmployee();
		workFlowActionDto.setOrgId(orgId);
		workFlowActionDto.setEmpId(emp.getEmpId());
		workFlowActionDto.setDecision(getWorkflowActionDto().getDecision());

		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class)
				.fetchAttachmentIdByAppid(getWorkflowActionDto().getApplicationId(), orgId);

		workFlowActionDto.setAttachementId(attacheMentIds);
		// this code is for document upload start
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.SocialSecurity.SERVICE_CODE,
				orgId);
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setOrgId(orgId);
		requestDTO.setDepartmentName(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		requestDTO.setServiceId(sm.getSmServiceId());
		requestDTO.setUserId(emp.getEmpId());
		requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());
		requestDTO.setDeptId(sm.getTbDepartment().getDpDeptid());
		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		docs = fileUpload.prepareFileUpload(docs);
		getApplicationformdto().setDocumentList(docs);
		// this code is for document upload end
		return schemeApplicationFormService.saveDecision(getApplicationformdto(), orgId, emp, workFlowActionDto,
				requestDTO);
	}

	public boolean validateInputs() {
		validateBean(this, ApplicationFormValidator.class);

		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}
   //Defect #138191
	public boolean validateSocialSecurityForm(List<SocialSecuritySchemeEligibilty> schemeEligibility,
			List<LookUp> lookupsFactor) {
		ApplicationFormDto dto=this.getApplicationformdto();
		boolean flag=false;
		for (SocialSecuritySchemeEligibilty socialElgble : schemeEligibility) {
			for (LookUp lookUp : lookupsFactor) {
				if(socialElgble.getFactorApplicableId()==(lookUp.getLookUpId())) {
					if(lookUp.getLookUpCode().contentEquals("AGE")) {
						if(dto.getAgeason()==null ||dto.getAgeason()==0) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.age.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("BPL")) {
						if(dto.getBplid()==null ||dto.getBplid()==0) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.bpl.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("EDU")) {
						if(dto.getEducationId()==null ||dto.getEducationId()==0) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.education.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("CTY")) {
						if(dto.getCategoryId()==null ||dto.getCategoryId()==0) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.category.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("DSY")) {
						if(dto.getTypeofDisId()==null ||dto.getTypeofDisId()==0) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.disability.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("GNR")) {
						if(dto.getGenderId()==null ||dto.getGenderId()==0) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.gender.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("ICE")) {
						if(dto.getAnnualIncome()==null ) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.income.ismadatory"));
							flag=true;
						}
					}
					if(lookUp.getLookUpCode().contentEquals("MLS")) {
						if(dto.getMaritalStatusId()==null||dto.getMaritalStatusId()==0 ) {
							this.addValidationError(ApplicationSession.getInstance().getMessage("social.marital.ismadatory"));
							flag=true;
						}
					}
					
				}
            
			}

		}
		return flag;
	}
	
	
	private void setOfflineDetailsDTO(CommonChallanDTO offline, SchemeApplicationFormModel model) {
		ApplicationFormDto requestDTO = model.getApplicationformdto();
		
		ServiceMaster serMast = serviceMasterService
				.getServiceMaster(model.getApplicationformdto().getSelectSchemeName(), UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setAmountToPay(offline.getAmountToShow().toString());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(requestDTO.getOrgId());
		offline.setDeptId(serMast.getTbDepartment().getDpDeptid());
		offline.setApplNo(requestDTO.getMasterAppId());
		offline.setServiceId(serMast.getSmServiceId());
		offline.setServiceCode(serMast.getSmShortdesc());
		offline.setApplicantName(requestDTO.getNameofApplicant());
		offline.setApplicantAddress(requestDTO.getApplicantAdress());
		offline.setMobileNumber(requestDTO.getMobNum().toString());
		offline.setEmailId(requestDTO.getEmail());
		offline.setLgIpMac(Utility.getMacAddress());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setWorkflowEnable(MainetConstants.FlagN);

		/*
		 * if (model.getCheckListApplFlag().equalsIgnoreCase("Y")) {
		 * offline.setDocumentUploaded(true); } else {
		 */
			offline.setDocumentUploaded(true);
		//}
		for (final Map.Entry<Long, Double> entry : this.getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		   setOfflineDTO(offline);
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			offline.setAmountToPay(model.getCharges().toString());

			if (offline.getOnlineOfflineCheck() != null
					&& MainetConstants.N_FLAG.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
				final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
				offline.setChallanValidDate(master.getChallanValiDate());
				offline.setChallanNo(master.getChallanNo());
				setOfflineDTO(offline);

			} else if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
				final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, serMast.getSmShortdesc());
				setReceiptDTO(printDto);
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				if (model.getApplicationformdto().getSubSchemeName() != null)
					model.getApplicationformdto().setObjOfschme(
							(CommonMasterUtility.getHierarchicalLookUp(model.getApplicationformdto().getSubSchemeName(),
									UserSession.getCurrent().getOrganisation().getOrgid())).getLookUpDesc());
				getReceiptDTO().setSubject(printDto.getSubject() + " " + model.getApplicationformdto().getObjOfschme());
			}

			}
	}


	public String getIsFamilyDetReq() {
		return isFamilyDetReq;
	}

	public void setIsFamilyDetReq(String isFamilyDetReq) {
		this.isFamilyDetReq = isFamilyDetReq;
	}

	public List<ApplicationFormDto> getApplicantDtoList() {
		return applicantDtoList;
	}

	public void setApplicantDtoList(List<ApplicationFormDto> applicantDtoList) {
		this.applicantDtoList = applicantDtoList;
	}

	public String getIsLifeCertificateReq() {
		return isLifeCertificateReq;
	}

	public void setIsLifeCertificateReq(String isLifeCertificateReq) {
		this.isLifeCertificateReq = isLifeCertificateReq;
	}

	public String getAnnualIncomeCheck() {
		return annualIncomeCheck;
	}

	public void setAnnualIncomeCheck(String annualIncomeCheck) {
		this.annualIncomeCheck = annualIncomeCheck;
	}

	public List<LookUp> getSubTypeList() {
		return subTypeList;
	}

	public void setSubTypeList(List<LookUp> subTypeList) {
		this.subTypeList = subTypeList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public List<String> getBanklt() {
		return banklt;
	}

	public void setBanklt(List<String> banklt) {
		this.banklt = banklt;
		
	}

}

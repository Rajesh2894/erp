package com.abm.mainet.tradeLicense.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
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
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.domain.TbMlOwnerDetail;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.repository.TradeLicenseApplicationRepository;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class LicenseGenerationModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServiceMaster serviceMaster = new ServiceMaster();
	private List<TbLoiMas> tbLoiMas = new ArrayList<>();
	private List<TradeMasterDetailDTO> tradeMasterDetailDTO = new ArrayList<>();
	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
	private String viewMode;
	private String saveMode;
	private Long rmRcptno;
	private BigDecimal rmAmount;
	private String payMode;
	private String dateDesc;
	private String loiDateDesc;
	private String issuanceDateDesc;
	private String licenseFromDateDesc;
	private Long taskId;
	private String filePath;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private String imagePath;
	private String categoryDesc;
	private String subCategoryDesc;
	private List<TbApprejMas> apprejMasList = new ArrayList<>();
	private Map<String, Double> chargeDescAndAmount = new LinkedHashMap<>();
	private String trdWard1Desc;
	private String trdWard2Desc;
	private String trdWard3Desc;
	private String trdWard4Desc;
	private String trdWard5Desc;
	private String ward1Level;
	private String ward2Level;
	private String ward3Level;
	private String ward4Level;
	private String ward5Level;
	private Department department;
	private BigDecimal depositeAmount;
	private BigDecimal licenseFee;
	private String categoryCode;
	
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;
	
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	TradeLicenseApplicationRepository tradeLicenseApplicationRepository;
	
	@Autowired
	 private ISMSAndEmailService iSMSAndEmailService;

	@Override
	public boolean saveForm() {

		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		TradeMasterDetailDTO masDto = getTradeDetailDTO();
		// added for Defect#106834
		BigDecimal triRate = masDto.getTradeLicenseItemDetailDTO().get(0).getTriRate();
		Date date = null;
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		String lookup = CommonMasterUtility.findLookUpCode("LIT", UserSession.getCurrent().getOrganisation().getOrgid(),
				masDto.getTrdLictype());
		if (!lookup.equals("P")) {
			date = masDto.getTrdLictoDate();
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, +1);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
			date = calendar.getTime();
		}
		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
		applicationContext.getBean(ITradeLicenseApplicationService.class).updateTradeLicenseStatusFlag(masDto,
				UserSession.getCurrent().getOrganisation().getOrgid(), masDto.getTrdStatus(), date, lgIpMacUpd);

		masDto = applicationContext.getBean(ITradeLicenseApplicationService.class)
				.getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());
		// added for Defect#106834
		masDto.getTradeLicenseItemDetailDTO().get(0).setTriRate(triRate);

		if (lookup.equalsIgnoreCase(MainetConstants.FlagP)) {

			TbDepartment department = ApplicationContextProvider.getApplicationContext()
					.getBean(DepartmentService.class)
					.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA, "ML");
			Long calculateLicMaxTenureDays = iTradeLicenseApplicationService.calculateLicMaxTenureDays(
					department.getDpDeptid(), serviceMaster.getSmServiceId(), null, orgid, masDto);

			if (calculateLicMaxTenureDays == null || calculateLicMaxTenureDays == 0l) {

				throw new FrameworkException("No range found in License Validity Master of permanat type");
			}

			Date toDate = Utility.getAddedDateBy2(new Date(), calculateLicMaxTenureDays.intValue());

			masDto.setTrdLicfromDate(new Date());
			masDto.setTrdLictoDate(toDate);
		}
		// 126060 on scrutiny form if view and modify Ans not checked then status not updating as A 
		masDto.getTradeLicenseItemDetailDTO().forEach(itemDto ->{
			itemDto.setTriStatus(MainetConstants.FlagA);			
		});
		
		masDto.setTradeLicenseItemDetailDTO(masDto.getTradeLicenseItemDetailDTO());
		// write outside of if block because need to save trade data for Defect#106834
		TbMlTradeMast masEntity = mapDtoToEntity(masDto);
//Defect#107828 for saving TB_ML_MST_History table
		iTradeLicenseApplicationService.saveTradeLicensePrintingData(masEntity);
		// tradeLicenseApplicationRepository.save(masEntity);
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setApplicationId(masDto.getApmApplicationId());
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		taskAction.setTaskId(taskId);

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName("scrutiny");
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			applicationContext.getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}

		this.setSuccessMessage(getAppSession().getMessage("trade.successLicenseMsg1") + " " + masDto.getTrdLicno() + " "
				+ getAppSession().getMessage("trade.successLicenseMsg2"));
		
		sendSmsEmail(masDto);
		
		if(masDto.getPmPropNo() != null && StringUtils.isNotEmpty(masDto.getPmPropNo())) {
			TradeMasterDetailDTO trdDto = iTradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(masDto);
			trdDto.setPmPropNo(masDto.getPmPropNo());
		    LookUp licCat = CommonMasterUtility.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgid);
		    LookUp licSubCat = CommonMasterUtility.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod2(),orgid);
		    trdDto.setLiceCategory(licCat.getLookUpDesc());
		    trdDto.setLiceSubCategory(licSubCat.getLookUpDesc());
		    trdDto.setTrdBusnm(masDto.getTrdBusnm());
		    trdDto.setTrdOwnerNm(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
			if(trdDto != null && trdDto.getpTaxCollEmpId() != null)
			this.sendSmsEmail(trdDto, "TradeApplicationForm.html",PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG);
		}
		return true;
	}

	//Us#97147
	private void sendSmsEmail(TradeMasterDetailDTO trdDto, String menuUrl, String msgType) {
		Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();		
		ServiceMaster sm = serviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,orgId);
		SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		smsDto.setOrgId(orgId);
		smsDto.setLangId(UserSession.getCurrent().getLanguageId());
		smsDto.setServName(sm.getSmServiceName());
		smsDto.setAppNo(String.valueOf(trdDto.getTrdLicno()));
		smsDto.setPropertyNo(trdDto.getPmPropNo());
		smsDto.setAppName(trdDto.getpTaxCollEmpName());
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		smsDto.setMobnumber(trdDto.getpTaxCollEmpMobNo());
		smsDto.setEmail(trdDto.getpTaxCollEmpEmailId());
		smsDto.setDate(new Date());
		smsDto.setType(trdDto.getLiceCategory());
		smsDto.setSubtype(trdDto.getLiceSubCategory());
		smsDto.setBusinessName(trdDto.getTrdBusnm());
		smsDto.setOwnerName(trdDto.getTrdOwnerNm());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, menuUrl, msgType, smsDto,
				UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
	}
	
	private TbMlTradeMast mapDtoToEntity(TradeMasterDetailDTO tradeMasterDto) {
		TbMlTradeMast masEntity = new TbMlTradeMast();
		List<TbMlItemDetail> itemdDetailsList = new ArrayList<>();
		List<TbMlOwnerDetail> ownerDetailsList = new ArrayList<>();

		BeanUtils.copyProperties(tradeMasterDto, masEntity);
		tradeMasterDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TbMlItemDetail itemEntity = new TbMlItemDetail();
			BeanUtils.copyProperties(itemdDetails, itemEntity);
			itemEntity.setMasterTradeId(masEntity);
			itemdDetailsList.add(itemEntity);
		});
		tradeMasterDto.getTradeLicenseOwnerdetailDTO().forEach(ownerDetails -> {
			TbMlOwnerDetail ownerEntity = new TbMlOwnerDetail();
			BeanUtils.copyProperties(ownerDetails, ownerEntity);
			ownerEntity.setMasterTradeId(masEntity);
			ownerDetailsList.add(ownerEntity);
		});

		masEntity.setOwnerDetails(ownerDetailsList);
		masEntity.setItemDetails(itemdDetailsList);
		masEntity.setTrdLicno(tradeMasterDto.getTrdLicno());
		return masEntity;
	}

	//126164
	private void sendSmsEmail(TradeMasterDetailDTO masDto) {
		final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		smsDto.setMobnumber(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		smsDto.setAppNo(masDto.getApmApplicationId().toString());
		smsDto.setAppName(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.TradeLicense.SERVICE_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		setServiceMaster(sm);
		smsDto.setServName(sm.getSmServiceName());
		smsDto.setEmail(masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		String url = "LicenseGeneration.html";
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = UserSession.getCurrent().getLanguageId();
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, url,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, smsDto, org, langId);
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<TbLoiMas> getTbLoiMas() {
		return tbLoiMas;
	}

	public void setTbLoiMas(List<TbLoiMas> tbLoiMas) {
		this.tbLoiMas = tbLoiMas;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public Long getRmRcptno() {
		return rmRcptno;
	}

	public void setRmRcptno(Long rmRcptno) {
		this.rmRcptno = rmRcptno;
	}

	public BigDecimal getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(BigDecimal rmAmount) {
		this.rmAmount = rmAmount;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<TradeMasterDetailDTO> getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(List<TradeMasterDetailDTO> tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public String getLoiDateDesc() {
		return loiDateDesc;
	}

	public void setLoiDateDesc(String loiDateDesc) {
		this.loiDateDesc = loiDateDesc;
	}

	public String getIssuanceDateDesc() {
		return issuanceDateDesc;
	}

	public void setIssuanceDateDesc(String issuanceDateDesc) {
		this.issuanceDateDesc = issuanceDateDesc;
	}

	public String getLicenseFromDateDesc() {
		return licenseFromDateDesc;
	}

	public void setLicenseFromDateDesc(String licenseFromDateDesc) {
		this.licenseFromDateDesc = licenseFromDateDesc;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public List<TbApprejMas> getApprejMasList() {
		return apprejMasList;
	}

	public void setApprejMasList(List<TbApprejMas> apprejMasList) {
		this.apprejMasList = apprejMasList;
	}

	public String getTrdWard1Desc() {
		return trdWard1Desc;
	}

	public String getTrdWard2Desc() {
		return trdWard2Desc;
	}

	public String getTrdWard3Desc() {
		return trdWard3Desc;
	}

	public String getTrdWard4Desc() {
		return trdWard4Desc;
	}

	public String getTrdWard5Desc() {
		return trdWard5Desc;
	}

	public void setTrdWard1Desc(String trdWard1Desc) {
		this.trdWard1Desc = trdWard1Desc;
	}

	public void setTrdWard2Desc(String trdWard2Desc) {
		this.trdWard2Desc = trdWard2Desc;
	}

	public void setTrdWard3Desc(String trdWard3Desc) {
		this.trdWard3Desc = trdWard3Desc;
	}

	public void setTrdWard4Desc(String trdWard4Desc) {
		this.trdWard4Desc = trdWard4Desc;
	}

	public void setTrdWard5Desc(String trdWard5Desc) {
		this.trdWard5Desc = trdWard5Desc;
	}

	public String getWard1Level() {
		return ward1Level;
	}

	public void setWard1Level(String ward1Level) {
		this.ward1Level = ward1Level;
	}

	public String getWard2Level() {
		return ward2Level;
	}

	public String getWard3Level() {
		return ward3Level;
	}

	public String getWard4Level() {
		return ward4Level;
	}

	public String getWard5Level() {
		return ward5Level;
	}

	public void setWard2Level(String ward2Level) {
		this.ward2Level = ward2Level;
	}

	public void setWard3Level(String ward3Level) {
		this.ward3Level = ward3Level;
	}

	public void setWard4Level(String ward4Level) {
		this.ward4Level = ward4Level;
	}

	public void setWard5Level(String ward5Level) {
		this.ward5Level = ward5Level;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public Map<String, Double> getChargeDescAndAmount() {
		return chargeDescAndAmount;
	}

	public void setChargeDescAndAmount(Map<String, Double> chargeDescAndAmount) {
		this.chargeDescAndAmount = chargeDescAndAmount;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public BigDecimal getDepositeAmount() {
		return depositeAmount;
	}

	public void setDepositeAmount(BigDecimal depositeAmount) {
		this.depositeAmount = depositeAmount;
	}

	public BigDecimal getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(BigDecimal licenseFee) {
		this.licenseFee = licenseFee;
	}

	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}

	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	
}
